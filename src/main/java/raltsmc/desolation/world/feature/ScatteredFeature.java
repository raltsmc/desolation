package raltsmc.desolation.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class ScatteredFeature extends Feature<ScatteredFeatureConfig> {
    public ScatteredFeature(Codec<ScatteredFeatureConfig> codec) { super(codec); }

    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random,
                            BlockPos blockPos, ScatteredFeatureConfig scatteredFeatureConfig) {
        if (random.nextDouble() > scatteredFeatureConfig.failChance) {
            BlockState blockState = scatteredFeatureConfig.stateProvider.getBlockState(random, blockPos);
            BlockPos blockPos3;
            if (scatteredFeatureConfig.project) {
                blockPos3 = structureWorldAccess.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, blockPos);
            } else {
                blockPos3 = blockPos;
            }

            int i = 0;
            Mutable mutable = new Mutable();
            mutable.set(blockPos3);

            for (int j = 0; j < scatteredFeatureConfig.tries; ++j) {
                boolean bl1 = random.nextInt(4) > 0;
                boolean bl2 = false;
                mutable.set(mutable,
                        random.nextInt(2) - random.nextInt(2),
                        random.nextInt(2) - random.nextInt(2),
                        random.nextInt(scatteredFeatureConfig.spreadZ + 1) - random.nextInt(scatteredFeatureConfig.spreadZ + 1))
                        .clamp(Axis.X, blockPos3.getX() - scatteredFeatureConfig.spreadX, blockPos3.getX() + scatteredFeatureConfig.spreadX)
                        .clamp(Axis.Y, blockPos3.getY() - scatteredFeatureConfig.spreadY, blockPos3.getY() + scatteredFeatureConfig.spreadY)
                        .clamp(Axis.Z, blockPos3.getZ() - scatteredFeatureConfig.spreadZ, blockPos3.getZ() + scatteredFeatureConfig.spreadZ);
                BlockPos blockPos4;

                if (bl1 && scatteredFeatureConfig.modifyGround && mutable.equals(structureWorldAccess.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, mutable).mutableCopy())) {
                    mutable.set(structureWorldAccess.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, mutable).add(0, -1, 0));
                    blockPos4 = mutable;
                    bl2 = true;
                } else {
                    blockPos4 = mutable.down();
                }
                BlockState blockState2 = structureWorldAccess.getBlockState(blockPos4);

                if ((structureWorldAccess.isAir(mutable) || (scatteredFeatureConfig.canReplace && structureWorldAccess.getBlockState(mutable).getMaterial().isReplaceable()) || bl2)
                        && blockState.canPlaceAt(structureWorldAccess, mutable)
                        && (scatteredFeatureConfig.whitelist.isEmpty() || scatteredFeatureConfig.whitelist.contains(blockState2.getBlock()))
                        && !scatteredFeatureConfig.blacklist.contains(blockState2)
                        && (!scatteredFeatureConfig.needsWater || structureWorldAccess.getFluidState(blockPos4.west()).isIn(FluidTags.WATER) || structureWorldAccess.getFluidState(blockPos4.east()).isIn(FluidTags.WATER) || structureWorldAccess.getFluidState(blockPos4.north()).isIn(FluidTags.WATER) || structureWorldAccess.getFluidState(blockPos4.south()).isIn(FluidTags.WATER))
                        && (scatteredFeatureConfig.genInWater || !(
                        structureWorldAccess.getFluidState(mutable).isIn(FluidTags.WATER) ||
                                structureWorldAccess.getFluidState(mutable.north()).isIn(FluidTags.WATER) ||
                                structureWorldAccess.getFluidState(mutable.south()).isIn(FluidTags.WATER) ||
                                structureWorldAccess.getFluidState(mutable.east()).isIn(FluidTags.WATER) ||
                                structureWorldAccess.getFluidState(mutable.west()).isIn(FluidTags.WATER)
                ))
                ) {
                    scatteredFeatureConfig.blockPlacer.generate(structureWorldAccess, mutable, blockState, random);
                    ++i;
                }

            }
            return i > 0;
        } else {
            return false;
        }
    }
}
