package raltsmc.desolation.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.tag.DesolationBlockTags;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class CharredBranchBlock extends LeavesBlock {
    // This has to be at least the maximum placeable taxicab distance in CharredFoliagePlacer.generate()
    // ... as configured in the placer configurations of DesolationConfiguredFeatures
    public static final int SUPPORTED_MAX_TAXICAB_DISTANCE = 13;

    // Overloaded meanings in LeavesBlock.DISTANCE
    public static final int DISTANCE_SUPPORTED = 6;
    public static final int DISTANCE_UNSUPPORTED = 7;

    // These are in ticks; the intended effect is to reduce repeated searches during continuous trunk breaking.
    public static final int MINIMUM_DELAY = 60;
    public static final int DELAY_SPREAD = 100;

    public CharredBranchBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(LeavesBlock.DISTANCE, DISTANCE_SUPPORTED)
                .with(LeavesBlock.PERSISTENT, false)
                .with(LeavesBlock.WATERLOGGED, false));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 0.35F;
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return state;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.get(LeavesBlock.PERSISTENT) &&
                state.get(LeavesBlock.DISTANCE) < DISTANCE_UNSUPPORTED &&
                CharredBranchBlock.findSupportingTrunk(world, pos).isEmpty()) {
            world.setBlockState(pos, state.with(LeavesBlock.DISTANCE, DISTANCE_UNSUPPORTED), 3);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState()
                .with(LeavesBlock.PERSISTENT, true)
                .with(LeavesBlock.WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    protected static void notifyLossOfSupport(World world, BlockPos trunkPos) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        findSupportedBranches(world, trunkPos).forEach((pos, state) -> {
            // Add a delay to reduce repeat checks, and spread the work over many seconds.
            world.scheduleBlockTick(pos, state.getBlock(), MINIMUM_DELAY + random.nextInt(DELAY_SPREAD));
        });
    }

    // Desolation branches are leaves that do not require contiguous support,
    // so we have to search the entire taxicab volume for blocks to notify.
    protected static HashMap<BlockPos, BlockState> findSupportedBranches(World world, BlockPos trunkPos) {
        HashMap<BlockPos, BlockState> found = new HashMap<>(256);
        int x, y, z, xLimit, zLimit;

        for (y = -SUPPORTED_MAX_TAXICAB_DISTANCE; y <= SUPPORTED_MAX_TAXICAB_DISTANCE; ++y) {
            xLimit = SUPPORTED_MAX_TAXICAB_DISTANCE - Math.abs(y);
            for (x = -xLimit; x <= xLimit; ++x) {
                zLimit = SUPPORTED_MAX_TAXICAB_DISTANCE - Math.abs(x) - Math.abs(y);
                for (z = -zLimit; z <= zLimit; ++z) {
                    BlockPos pos = trunkPos.add(x, y, z);
                    BlockState state = world.getBlockState(pos);

                    if (state.isOf(DesolationBlocks.CHARRED_BRANCHES) &&
                            !state.get(LeavesBlock.PERSISTENT) &&
                            state.get(LeavesBlock.DISTANCE) < DISTANCE_UNSUPPORTED) {
                        found.put(pos, state);
                    }
                }
            }
        }

        return found;
    }

    // Desolation branches are leaves that do not require contiguous support,
    // so we may need to search the entire taxicab volume for a supporting log.
    protected static Optional<BlockPos> findSupportingTrunk(World world, BlockPos branchPos) {
        int x, y, z, xLimit, zLimit;

        for (y = -SUPPORTED_MAX_TAXICAB_DISTANCE; y <= SUPPORTED_MAX_TAXICAB_DISTANCE; ++y) {
            xLimit = SUPPORTED_MAX_TAXICAB_DISTANCE - Math.abs(y);
            for (x = -xLimit; x <= xLimit; ++x) {
                zLimit = SUPPORTED_MAX_TAXICAB_DISTANCE - Math.abs(x) - Math.abs(y);
                for (z = -zLimit; z <= zLimit; ++z) {
                    BlockPos pos = branchPos.add(x, y, z);
                    BlockState state = world.getBlockState(pos);

                    if (state.isIn(DesolationBlockTags.CHARRED_LOGS)) {
                        return Optional.of(pos);
                    }
                }
            }
        }

        return Optional.empty();
    }
}
