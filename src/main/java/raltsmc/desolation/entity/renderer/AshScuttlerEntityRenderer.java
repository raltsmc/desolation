package raltsmc.desolation.entity.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import raltsmc.desolation.entity.AshScuttlerEntity;
import raltsmc.desolation.entity.BlackenedEntity;
import raltsmc.desolation.entity.model.AshScuttlerEntityModel;
import raltsmc.desolation.entity.renderer.feature.GeoGlowLayerRenderer;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class AshScuttlerEntityRenderer extends GeoEntityRenderer<AshScuttlerEntity> {
    public AshScuttlerEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new AshScuttlerEntityModel());
        this.addLayer(new GeoGlowLayerRenderer<AshScuttlerEntity>(this, "textures/entity/ash_scuttler_glow.png"));
    }
}
