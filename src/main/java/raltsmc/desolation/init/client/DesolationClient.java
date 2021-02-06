package raltsmc.desolation.init.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import raltsmc.desolation.client.renderer.AshSiphonBlockEntityRenderer;
import raltsmc.desolation.client.renderer.AshScuttlerEntityRenderer;
import raltsmc.desolation.client.renderer.BlackenedEntityRenderer;
import raltsmc.desolation.item.armor.TinkererArmorItem;
import raltsmc.desolation.client.renderer.TinkererArmorRenderer;
import raltsmc.desolation.client.renderer.AshSiphonBlockItemRenderer;
import raltsmc.desolation.registry.DesolationBlockEntities;
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.registry.DesolationEntities;
import raltsmc.desolation.registry.DesolationItems;
import software.bernie.geckolib3.renderer.geo.GeoArmorRenderer;
import software.bernie.geckolib3.renderer.geo.GeoItemRenderer;

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

        BlockEntityRendererRegistry.INSTANCE.register(DesolationBlockEntities.ASH_SIPHON_BLOCK_ENTITY, AshSiphonBlockEntityRenderer::new);
        GeoItemRenderer.registerItemRenderer(DesolationItems.ASH_SIPHON, new AshSiphonBlockItemRenderer());

        GeoArmorRenderer.registerArmorRenderer(TinkererArmorItem.class, new TinkererArmorRenderer());
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
