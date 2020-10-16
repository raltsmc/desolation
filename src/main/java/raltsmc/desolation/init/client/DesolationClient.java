package raltsmc.desolation.init.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import raltsmc.desolation.entity.AshScuttlerEntityRenderer;
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.registry.DesolationEntities;

@Environment(EnvType.CLIENT)
public class DesolationClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(DesolationBlocks.CHARRED_BRANCHES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DesolationBlocks.ASH_BRAMBLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DesolationBlocks.SCORCHED_TUFT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DesolationBlocks.CINDERFRUIT_PLANT, RenderLayer.getCutout());

        EntityRendererRegistry.INSTANCE.register(DesolationEntities.ASH_SCUTTLER, (dispatcher, context) -> {
            return new AshScuttlerEntityRenderer(dispatcher);
        });
    }
}
