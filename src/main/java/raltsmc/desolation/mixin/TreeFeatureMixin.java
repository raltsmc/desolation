package raltsmc.desolation.mixin;


import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import raltsmc.desolation.registry.DesolationBlocks;

import java.util.Random;
import java.util.Set;

import static net.minecraft.world.gen.feature.Feature.isSoil;

@Mixin(TreeFeature.class)
public class TreeFeatureMixin {
    @Inject(method = "isDirtOrGrass", at = @At("HEAD"), cancellable = true)
    private static void isDirtOrGrass(TestableWorld world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        boolean isValid = world.testBlockState(pos, blockState -> {
            Block block = blockState.getBlock();
            return isSoil(block) || block == Blocks.FARMLAND || block == DesolationBlocks.CHARRED_SOIL;
        });
        info.setReturnValue(isValid);
    }

    // janky as fuck but if it works......
    // (it didnt)

    /*@Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    private void generate(ModifiableTestableWorld world, Random random, BlockPos pos, Set<BlockPos> logPositions,
                          Set<BlockPos> leavesPositions, BlockBox box, TreeFeatureConfig config,
                          CallbackInfoReturnable<Boolean> info) {
        if (config.trunkPlacer.getClass() == StraightTrunkPlacer.class) {
            info.setReturnValue(canGenerateCharredTree(world, pos));
        }
    }

    private boolean canGenerateCharredTree(ModifiableTestableWorld world, BlockPos pos) {
        for (int i = 1; i <= 2; ++i) {
            for (int y = 0; y <= 255 - pos.getY(); ++y) {
                for (int x = -i; x <= i; ++i) {
                    for (int z = -i; z <= i; ++z) {
                        BlockPos posT = new BlockPos(pos.getX() + x, y, pos.getZ() + z);
                        if (world.testBlockState(posT, blockState -> blockState == DesolationBlocks.CHARRED_LOG.getDefaultState())) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }*/
}
