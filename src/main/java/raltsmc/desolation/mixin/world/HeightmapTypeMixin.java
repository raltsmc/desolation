package raltsmc.desolation.mixin.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import raltsmc.desolation.block.CharredBranchBlock;

@Mixin(Heightmap.Type.class)
public class HeightmapTypeMixin {
    @Redirect(method = "method_16686", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"))
    private static Block branchRedirect(BlockState state) {
        // hacky but idgaf :)
        return state.getBlock() instanceof CharredBranchBlock ? Blocks.OAK_LEAVES : state.getBlock();
    }
}
