package raltsmc.desolation.mixin.world.gen.feature;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import raltsmc.desolation.registry.DesolationBlocks;

@Mixin(TreeFeature.class)
public class TreeFeatureMixin {
    @Inject(method = "isDirtOrGrass", at = @At("HEAD"), cancellable = true)
    private static void isDirtOrGrass(TestableWorld world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        if (world.testBlockState(pos, blockState -> blockState.getBlock() == DesolationBlocks.CHARRED_SOIL)) {
            info.setReturnValue(true);
        }
    }
}
