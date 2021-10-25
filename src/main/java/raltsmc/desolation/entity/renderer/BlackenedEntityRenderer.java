package raltsmc.desolation.entity.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import raltsmc.desolation.entity.BlackenedEntity;
import raltsmc.desolation.entity.model.BlackenedEntityModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BlackenedEntityRenderer extends GeoEntityRenderer<BlackenedEntity> {
    public BlackenedEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BlackenedEntityModel());
    }
}
