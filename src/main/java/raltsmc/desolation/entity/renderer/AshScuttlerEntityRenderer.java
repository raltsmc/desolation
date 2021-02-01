package raltsmc.desolation.entity.renderer;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.entity.AshScuttlerEntity;
import raltsmc.desolation.entity.model.AshScuttlerEntityModel;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class AshScuttlerEntityRenderer extends GeoEntityRenderer<AshScuttlerEntity> {
    public AshScuttlerEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new AshScuttlerEntityModel());
    }
}
