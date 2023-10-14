package raltsmc.desolation.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import raltsmc.desolation.entity.BlackenedEntity;
import raltsmc.desolation.client.render.entity.model.BlackenedEntityModel;
import raltsmc.desolation.client.render.entity.feature.GeoGlowLayerRenderer;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BlackenedEntityRenderer extends GeoEntityRenderer<BlackenedEntity> {
    public BlackenedEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BlackenedEntityModel());
        this.addRenderLayer(new GeoGlowLayerRenderer<>(this, "textures/entity/blackened_glow.png"));
    }
}
