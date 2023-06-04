package raltsmc.desolation.world.feature;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.foliage.FoliagePlacer;

import java.util.HashSet;

public class ScatteredFeature extends Feature<ScatteredFeatureConfig> {
    public ScatteredFeature(Codec<ScatteredFeatureConfig> codec) { super(codec); }

    @Override
    public boolean generate(FeatureContext<ScatteredFeatureConfig> context) {
        final HashSet<BlockPos> blockPlacements = Sets.newHashSet();
        final StructureWorldAccess structureWorldAccess = context.getWorld();
        FoliagePlacer.BlockPlacer blockPlacer = new FoliagePlacer.BlockPlacer() {
            @Override
            public void placeBlock(BlockPos pos, BlockState state) {
                blockPlacements.add(pos.toImmutable());
                structureWorldAccess.setBlockState(pos, state, 19);
            }

            @Override
            public boolean hasPlacedBlock(BlockPos pos) {
                return blockPlacements.contains(pos);
            }
        };
        return this.generate(context.getWorld(), context.getRandom(), context.getOrigin(), blockPlacer, context.getConfig());
    }

    private boolean generate(StructureWorldAccess world, Random random, BlockPos pos, FoliagePlacer.BlockPlacer blockPlacer, ScatteredFeatureConfig config) {
        if (random.nextDouble() > config.failChance) {
            BlockState blockState = config.stateProvider.get(random, pos);
            BlockPos blockPos3;
            if (config.project) {
                blockPos3 = world.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, pos);
            } else {
                blockPos3 = pos;
            }

            int i = 0;
            Mutable mutable = new Mutable();
            mutable.set(blockPos3);

            for (int j = 0; j < config.tries; ++j) {
                boolean bl1 = random.nextInt(4) > 0;
                boolean bl2 = false;
                mutable.set(mutable,
                        random.nextInt(2) - random.nextInt(2),
                        random.nextInt(2) - random.nextInt(2),
                        random.nextInt(config.spreadZ + 1) - random.nextInt(config.spreadZ + 1))
                        .clamp(Axis.X, blockPos3.getX() - config.spreadX, blockPos3.getX() + config.spreadX)
                        .clamp(Axis.Y, blockPos3.getY() - config.spreadY, blockPos3.getY() + config.spreadY)
                        .clamp(Axis.Z, blockPos3.getZ() - config.spreadZ, blockPos3.getZ() + config.spreadZ);
                BlockPos blockPos4;

                if (bl1 && config.modifyGround && mutable.equals(world.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, mutable).mutableCopy())) {
                    mutable.set(world.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, mutable).add(0, -1, 0));
                    blockPos4 = mutable;
                    bl2 = true;
                } else {
                    blockPos4 = mutable.down();
                }
                BlockState blockState2 = world.getBlockState(blockPos4);

                if ((world.isAir(mutable) || (config.canReplace && world.getBlockState(mutable).getMaterial().isReplaceable()) || bl2)
                        && blockState.canPlaceAt(world, mutable)
                        && (config.whitelist.isEmpty() || config.whitelist.contains(blockState2))
                        && !config.blacklist.contains(blockState2)
                        && (!config.needsWater || world.getFluidState(blockPos4.west()).isIn(FluidTags.WATER) || world.getFluidState(blockPos4.east()).isIn(FluidTags.WATER) || world.getFluidState(blockPos4.north()).isIn(FluidTags.WATER) || world.getFluidState(blockPos4.south()).isIn(FluidTags.WATER))
                        && (config.genInWater || !(
                        world.getFluidState(mutable).isIn(FluidTags.WATER) ||
                                world.getFluidState(mutable.north()).isIn(FluidTags.WATER) ||
                                world.getFluidState(mutable.south()).isIn(FluidTags.WATER) ||
                                world.getFluidState(mutable.east()).isIn(FluidTags.WATER) ||
                                world.getFluidState(mutable.west()).isIn(FluidTags.WATER)
                ))
                ) {
                    blockPlacer.placeBlock(mutable, config.stateProvider.get(random, mutable));
                    ++i;
                }

            }
            return i > 0;
        } else {
            return false;
        }
    }
}
