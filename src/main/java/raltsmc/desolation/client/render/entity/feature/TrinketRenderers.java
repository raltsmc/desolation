package raltsmc.desolation.client.render.entity.feature;

import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.client.render.entity.model.HeadTrinketModel;
import raltsmc.desolation.registry.DesolationItems;

import static raltsmc.desolation.init.client.DesolationClient.HEAD_GOGGLES_LAYER;
import static raltsmc.desolation.init.client.DesolationClient.HEAD_MASK_LAYER;

public class TrinketRenderers implements SimpleSynchronousResourceReloadListener {

    @Override
    public void reload(ResourceManager manager) {
        TrinketRendererRegistry.registerRenderer(DesolationItems.MASK, new HeadTrinketRenderer("textures/entity/ash_tinkerer_headgear.png", new HeadTrinketModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(HEAD_MASK_LAYER))));
        TrinketRendererRegistry.registerRenderer(DesolationItems.GOGGLES, new HeadTrinketRenderer("textures/entity/ash_tinkerer_headgear.png",  new HeadTrinketModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(HEAD_GOGGLES_LAYER), RenderLayer::getEntityTranslucent)));
    }

    @Override
    public Identifier getFabricId() {
        return Desolation.id("trinket_renderers");
    }
}
