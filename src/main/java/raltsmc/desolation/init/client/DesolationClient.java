package raltsmc.desolation.init.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import software.bernie.geckolib3.ArmorRenderingRegistry;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.access.PlayerEntityAccess;
import raltsmc.desolation.entity.renderer.AshScuttlerEntityRenderer;
import raltsmc.desolation.entity.renderer.BlackenedEntityRenderer;
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.registry.DesolationEntities;
import raltsmc.desolation.registry.DesolationItems;

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

        EntityRendererRegistry.INSTANCE.register(DesolationEntities.ASH_SCUTTLER, (dispatcher, context) -> new AshScuttlerEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(DesolationEntities.BLACKENED, (dispatcher, context) -> new BlackenedEntityRenderer(dispatcher));

        /*ArmorRenderingRegistry.registerSimpleTexture(Desolation.id("textures/models/armor/mask"),
                DesolationItems.MASK);
        ArmorRenderingRegistry.registerSimpleTexture(Desolation.id("textures/models/armor/goggles"), DesolationItems.GOGGLES);
        ArmorRenderingRegistry.registerSimpleTexture(Desolation.id("textures/models/armor/mask_and_goggles"),
        DesolationItems.MASK_GOGGLES);*/

        /*ArmorRenderingRegistry.registerSimpleTexture(Desolation.id("headgear_mask"), DesolationItems.MASK);
        ArmorRenderingRegistry.registerSimpleTexture(Desolation.id("headgear_goggles"), DesolationItems.GOGGLES);
        ArmorRenderingRegistry.registerSimpleTexture(Desolation.id("headgear_mask_and_goggles"), DesolationItems.MASK_GOGGLES);
*/


        /*ArmorRenderingRegistry.registerSimpleTexture(Desolation.id("mask"), DesolationItems.MASK);
        ArmorRenderingRegistry.registerSimpleTexture(Desolation.id("goggles"), DesolationItems.GOGGLES);
        ArmorRenderingRegistry.registerSimpleTexture(Desolation.id("mask_and_goggles"), DesolationItems.MASK_GOGGLES);*/

        ArmorRenderingRegistry.registerTexture((entity, stack, slot, defaultTexture) -> "textures/mask.png", DesolationItems.MASK);
        ArmorRenderingRegistry.registerTexture((entity, stack, slot, defaultTexture) -> "textures/goggles.png", DesolationItems.GOGGLES);
        ArmorRenderingRegistry.registerTexture((entity, stack, slot, defaultTexture) -> "textures/mask_and_goggles.png", DesolationItems.MASK_GOGGLES);


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
