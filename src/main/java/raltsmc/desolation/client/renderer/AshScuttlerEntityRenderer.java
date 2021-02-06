package raltsmc.desolation.client.renderer;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import raltsmc.desolation.entity.AshScuttlerEntity;
import raltsmc.desolation.client.model.AshScuttlerEntityModel;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class AshScuttlerEntityRenderer extends GeoEntityRenderer<AshScuttlerEntity> {
    public AshScuttlerEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new AshScuttlerEntityModel());
    }
}
