package raltsmc.desolation.entity.renderer;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import raltsmc.desolation.entity.AshScuttlerEntity;
import raltsmc.desolation.entity.model.AshScuttlerEntityModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class AshScuttlerEntityRenderer extends GeoEntityRenderer<AshScuttlerEntity> {
    public AshScuttlerEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new AshScuttlerEntityModel());
    }
}
