package raltsmc.desolation.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class EmberBlock extends Block {
    private final BlockState cooledState;

    public EmberBlock(Block cooled, Settings settings) {
        super(settings);
        this.cooledState = cooled.getDefaultState();
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient && !entity.isFireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            entity.damage(DamageSource.HOT_FLOOR, 1.0F);
            if (Math.random() > 0.9D) {
                entity.setFireTicks(120);
            }
        }

        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState blockstate, World world, BlockPos pos, Random random) {
        double d = (double)pos.getX() + 0.5D;
        double e = (double)pos.getY();
        double f = (double)pos.getZ() + 0.5D;

        double g = random.nextDouble() * 0.6D - 0.3D;
        double h = random.nextDouble() * 6.0D / 16.0D;
        double i = (random.nextDouble() - 0.5D) / 5.0D;

        world.addParticle(ParticleTypes.LARGE_SMOKE, d + g, e + h, f + g, 0.0D, 0.1D + i, 0.0D);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockView blockView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = blockView.getBlockState(blockPos);
        return shouldCool(blockView, blockPos, blockState) ? this.cooledState : super.getPlacementState(ctx);
    }

    private static boolean shouldCool(BlockView world, BlockPos pos, BlockState state) {
        return coolsIn(state) || coolsOnAnySide(world, pos) != CoolType.NONE;
    }

    private enum CoolType {
        NONE,
        TOUCHED_WATER,
        SMOTHERED
    }

    private static CoolType coolsOnAnySide(BlockView world, BlockPos pos) {
        boolean isTouchingWater = false;
        boolean isSmothered = true;
        BlockPos.Mutable mutable = pos.mutableCopy();
        Direction[] dirs = Direction.values();

        for (Direction direction : dirs) {
            BlockState blockState = world.getBlockState(mutable);
            if (direction != Direction.DOWN || coolsIn(blockState)) {
                mutable.set(pos, direction);
                blockState = world.getBlockState(mutable);
                if (coolsIn(blockState) && !blockState.isSideSolidFullSquare(world, pos, direction.getOpposite())) {
                    isTouchingWater = true;
                    break;
                } else if (!blockState.isOpaqueFullCube(world, mutable)) {
                    isSmothered = false;
                }
            }
        }

        if (isTouchingWater) {
            return CoolType.TOUCHED_WATER;
        } else if (isSmothered) {
            return CoolType.SMOTHERED;
        } else {
            return CoolType.NONE;
        }
    }

    private static boolean coolsIn(BlockState state) {
        return state.getFluidState().isIn(FluidTags.WATER);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        CoolType coolType = coolsOnAnySide(world, pos);
        if (coolType != CoolType.NONE) {
            if (!world.isClient() && coolType == CoolType.TOUCHED_WATER) {
                world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f);
            }
            return this.cooledState;
        } else {
            return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
        }
    }
}
