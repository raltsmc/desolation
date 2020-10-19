package raltsmc.desolation.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import raltsmc.desolation.registry.DesolationBlocks;

import java.util.Iterator;
import java.util.Random;

public class GiantBoulderFeature extends Feature<SingleStateFeatureConfig> {
    public GiantBoulderFeature(Codec<SingleStateFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random,
                            BlockPos blockPos, SingleStateFeatureConfig singleStateFeatureConfig) {
        for(; blockPos.getY() > 3; blockPos = blockPos.down()) {
            if (!structureWorldAccess.isAir(blockPos.down())) {
                Block block = structureWorldAccess.getBlockState(blockPos.down()).getBlock();
                if (block == DesolationBlocks.CHARRED_SOIL) {
                    break;
                }
            }
        }

        if (blockPos.getY() <= 3) {
            return false;
        } else {
            BlockPos blockPosB = blockPos;
            for (int i = 0; i < 18; ++i) {
                int j = (int)(random.nextInt(4) * random.nextDouble());
                int k = (int)(random.nextInt(4) * random.nextDouble());
                int l = (int)(random.nextInt(4) * random.nextDouble());
                float f = (float)(j+k+l) * random.nextFloat() + 0.5F;
                Iterator var11 =
                        BlockPos.iterate(blockPos.add(-j * (0.5+0.75*random.nextDouble()), -k * (0.5+0.75*random.nextDouble()), -l * (0.5+0.75*random.nextDouble())),
                                blockPos.add(j * (0.5+0.75*random.nextDouble()), k * (0.5+0.75*random.nextDouble()), l * (0.5+0.75*random.nextDouble()))).iterator();

                while(var11.hasNext()) {
                    BlockPos blockPos2 = ((BlockPos)var11.next());
                    if (blockPos2.getSquaredDistance(blockPos) <= (double)(f * f)) {
                        structureWorldAccess.setBlockState(blockPos2, singleStateFeatureConfig.state, 4);
                    }
                }
            }

            for (int i = 0; i < 54; ++i) {
                int j = (int)(random.nextInt(5) * random.nextDouble());
                int k = (int)(random.nextInt(5) * random.nextDouble());
                int l = (int)(random.nextInt(5) * random.nextDouble());
                float f = (float)(j+k+l) * random.nextFloat() + 0.5F;
                Iterator var11 = blockPosB.iterate(
                                blockPosB.add(-j * (0.5+0.75*random.nextDouble()), -k * (0.5+0.75*random.nextDouble()), -l * (0.5+0.75*random.nextDouble())),
                                blockPosB.add(j * (0.5+0.75*random.nextDouble()), k * (0.5+0.75*random.nextDouble()), l * (0.5+0.75*random.nextDouble()))
                ).iterator();

                while(var11.hasNext()) {
                    BlockPos blockPosB2 = ((BlockPos)var11.next()).add(-1 + random.nextInt(2), -1 + random.nextInt(2), -1 + random.nextInt(2));
                    if (blockPosB2.getSquaredDistance(blockPosB) <= (double)(f * f)) {
                        structureWorldAccess.setBlockState(blockPosB2, singleStateFeatureConfig.state, 4);
                    }
                }
            }

            return true;
        }
    }
}
