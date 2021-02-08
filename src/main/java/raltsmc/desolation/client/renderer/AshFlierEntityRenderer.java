package raltsmc.desolation.client.renderer;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import raltsmc.desolation.client.model.AshFlierEntityModel;
import raltsmc.desolation.entity.AshFlierEntity;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class AshFlierEntityRenderer extends GeoEntityRenderer<AshFlierEntity> {
    public AshFlierEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new AshFlierEntityModel());
    }
}
