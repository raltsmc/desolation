package raltsmc.desolation.mixin.world;

import net.minecraft.block.BlockState;
import net.minecraft.world.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import raltsmc.desolation.registry.DesolationBlocks;

@Mixin(Heightmap.Type.class)
public class HeightmapTypeMixin {
    @Inject(method = "method_16686", at = @At("HEAD"), cancellable = true)
    private static void branchRedirect(BlockState state, CallbackInfoReturnable<Boolean> ci) {
        // hacky but idgaf :)
        if (state.isOf(DesolationBlocks.CHARRED_BRANCHES)) {
            ci.setReturnValue(false);
        }
    }
}
