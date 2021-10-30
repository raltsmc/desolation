package raltsmc.desolation.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import raltsmc.desolation.entity.AshScuttlerEntity;
import raltsmc.desolation.client.render.entity.model.AshScuttlerEntityModel;
import raltsmc.desolation.client.render.entity.feature.GeoGlowLayerRenderer;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class AshScuttlerEntityRenderer extends GeoEntityRenderer<AshScuttlerEntity> {
    public AshScuttlerEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new AshScuttlerEntityModel());
        this.addLayer(new GeoGlowLayerRenderer<AshScuttlerEntity>(this, "textures/entity/ash_scuttler_glow.png"));
    }
}
