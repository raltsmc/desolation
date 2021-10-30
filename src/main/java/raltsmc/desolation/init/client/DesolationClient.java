package raltsmc.desolation.init.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.impl.client.particle.ParticleFactoryRegistryImpl;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.client.particle.SparkParticle;
import raltsmc.desolation.client.render.entity.AshScuttlerEntityRenderer;
import raltsmc.desolation.client.render.entity.BlackenedEntityRenderer;
import raltsmc.desolation.client.render.entity.feature.HeadGearFeatureRenderer;
import raltsmc.desolation.client.render.entity.model.GogglesEntityModel;
import raltsmc.desolation.client.render.entity.model.MaskEntityModel;
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.registry.DesolationEntities;
import raltsmc.desolation.registry.DesolationParticles;

@Environment(EnvType.CLIENT)
public class DesolationClient implements ClientModInitializer {

    public static KeyBinding cinderDashBinding;
    public static final EntityModelLayer HEAD_MASK_LAYER = new EntityModelLayer(Desolation.id("mask"), "main");
    public static final EntityModelLayer HEAD_GOGGLES_LAYER = new EntityModelLayer(Desolation.id("goggles"), "main");

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(DesolationBlocks.CHARRED_BRANCHES, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(DesolationBlocks.ASH_BRAMBLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DesolationBlocks.CHARRED_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DesolationBlocks.SCORCHED_TUFT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DesolationBlocks.CINDERFRUIT_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DesolationBlocks.CHARRED_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DesolationBlocks.CHARRED_DOOR, RenderLayer.getCutout());

        EntityRendererRegistry.register(DesolationEntities.ASH_SCUTTLER, AshScuttlerEntityRenderer::new);
        EntityRendererRegistry.register(DesolationEntities.BLACKENED, BlackenedEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(HEAD_MASK_LAYER, MaskEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HEAD_GOGGLES_LAYER, GogglesEntityModel::getTexturedModelData);

//        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer,
//                                                                        registrationHelper, context) -> {
//            if (entityRenderer instanceof PlayerEntityRenderer) {
//                registrationHelper.register(new HeadGearFeatureRenderer<>(entityRenderer));
//            }
//        })

        ParticleFactoryRegistryImpl.INSTANCE.register(DesolationParticles.SPARK, SparkParticle.Factory::new);
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
