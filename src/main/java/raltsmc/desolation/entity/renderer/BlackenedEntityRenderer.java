package raltsmc.desolation.entity.renderer;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import raltsmc.desolation.entity.BlackenedEntity;
import raltsmc.desolation.entity.model.BlackenedEntityModel;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class BlackenedEntityRenderer extends GeoEntityRenderer<BlackenedEntity> {
    public BlackenedEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new BlackenedEntityModel());
    }
}
