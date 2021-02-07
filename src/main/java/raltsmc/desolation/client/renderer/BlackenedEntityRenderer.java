package raltsmc.desolation.client.renderer;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import raltsmc.desolation.client.model.BlackenedEntityModel;
import raltsmc.desolation.entity.BlackenedEntity;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class BlackenedEntityRenderer extends GeoEntityRenderer<BlackenedEntity> {
    public BlackenedEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new BlackenedEntityModel());
    }
}
