package raltsmc.desolation.init.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import raltsmc.desolation.access.PlayerEntityAccess;
import raltsmc.desolation.entity.renderer.AshScuttlerEntityRenderer;
import raltsmc.desolation.entity.renderer.BlackenedEntityRenderer;
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.registry.DesolationEntities;

@Environment(EnvType.CLIENT)
public class DesolationClient implements ClientModInitializer {

    public static KeyBinding cinderDashBinding;

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(DesolationBlocks.CHARRED_BRANCHES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DesolationBlocks.ASH_BRAMBLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DesolationBlocks.SCORCHED_TUFT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DesolationBlocks.CINDERFRUIT_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DesolationBlocks.CHARRED_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DesolationBlocks.CHARRED_DOOR, RenderLayer.getCutout());

        EntityRendererRegistry.INSTANCE.register(DesolationEntities.ASH_SCUTTLER, (dispatcher, context) -> {
            return new AshScuttlerEntityRenderer(dispatcher);
        });
        EntityRendererRegistry.INSTANCE.register(DesolationEntities.BLACKENED, (dispatcher, context) -> {
            return new BlackenedEntityRenderer(dispatcher);
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (cinderDashBinding.wasPressed()) {
                if (client.player != null) {
                    ((PlayerEntityAccess)client.player).doDash();
                }
            }
        });
    }

    static {
        cinderDashBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.desolation.cinder_dash",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.desolation.cat"
        ));
    }
}
