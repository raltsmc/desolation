package raltsmc.desolation.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import raltsmc.desolation.registry.DesolationBlocks;

public class GiantBoulderFeature extends Feature<SingleStateFeatureConfig> {
    public GiantBoulderFeature(Codec<SingleStateFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<SingleStateFeatureConfig> context) {
        return this.generate(context.getWorld(), context.getGenerator(), context.getRandom(), context.getOrigin(),
                context.getConfig());
    }

    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random,
                            BlockPos blockPos, SingleStateFeatureConfig singleStateFeatureConfig) {

        for (; blockPos.getY() > 3; blockPos = blockPos.down()) {
            if (!structureWorldAccess.isAir(blockPos.down())) {
                Block block = structureWorldAccess.getBlockState(blockPos.down()).getBlock();
                if (block == DesolationBlocks.CHARRED_SOIL) {
                    break;
                }
            }
        }

        if (blockPos.getY() <= 3 && random.nextDouble() > 0.32) {
            return false;
        } else if (structureWorldAccess.testFluidState(blockPos, fluidState -> !fluidState.isEmpty())) {
            return false;
        } else {
            for (int i = 0; i < 18; ++i) {
                int j = (int)(random.nextInt(4) * random.nextDouble());
                int k = (int)(random.nextInt(4) * random.nextDouble());
                int l = (int)(random.nextInt(4) * random.nextDouble());
                float f = (float)(j+k+l) * random.nextFloat() + 0.5F;

                for (BlockPos blockPos2 : BlockPos.iterate(
                        blockPos.add((int) (-j * (0.5 + 0.75 * random.nextDouble())), (int) (-k * (0.5 + 0.75 * random.nextDouble())), (int) (-l * (0.5 + 0.75 * random.nextDouble()))),
                        blockPos.add((int) (j * (0.5 + 0.75 * random.nextDouble())), (int) (k * (0.5 + 0.75 * random.nextDouble())), (int) (l * (0.5 + 0.75 * random.nextDouble()))))) {
                    if (blockPos2.getSquaredDistance(blockPos) <= (double) (f * f)) {
                        structureWorldAccess.setBlockState(blockPos2, singleStateFeatureConfig.state, 4);
                    }
                }
            }

            for (int i = 0; i < 54; ++i) {
                int j = (int)(random.nextInt(5) * random.nextDouble());
                int k = (int)(random.nextInt(5) * random.nextDouble());
                int l = (int)(random.nextInt(5) * random.nextDouble());
                float f = (float)(j+k+l) * random.nextFloat() + 0.5F;

                for (BlockPos pos : BlockPos.iterate(
                        blockPos.add((int) (-j * (0.5 + 0.75 * random.nextDouble())), (int) (-k * (0.5 + 0.75 * random.nextDouble())), (int) (-l * (0.5 + 0.75 * random.nextDouble()))),
                        blockPos.add((int) (j * (0.5 + 0.75 * random.nextDouble())), (int) (k * (0.5 + 0.75 * random.nextDouble())), (int) (l * (0.5 + 0.75 * random.nextDouble())))
                )) {
                    BlockPos blockPosB2 = pos.add(-1 + random.nextInt(2), -1 + random.nextInt(2), -1 + random.nextInt(2));
                    if (blockPosB2.getSquaredDistance(blockPos) <= (double) (f * f)) {
                        structureWorldAccess.setBlockState(blockPosB2, singleStateFeatureConfig.state, 4);
                    }
                }
            }

            return true;
        }
    }
}
