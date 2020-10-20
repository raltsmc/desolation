package raltsmc.desolation.entity.renderer;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.entity.AshScuttlerEntity;
import raltsmc.desolation.entity.model.AshScuttlerEntityModel;

public class AshScuttlerEntityRenderer extends MobEntityRenderer<AshScuttlerEntity, AshScuttlerEntityModel> {
    public AshScuttlerEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new AshScuttlerEntityModel(), 0.3f);
    }

    @Override
    public Identifier getTexture(AshScuttlerEntity entity) {
        return Desolation.id("textures/entity/ash_scuttler.png");
    }
}
