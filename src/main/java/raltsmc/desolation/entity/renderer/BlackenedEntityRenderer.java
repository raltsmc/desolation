package raltsmc.desolation.entity.renderer;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.entity.BlackenedEntity;
import raltsmc.desolation.entity.model.BlackenedEntityModel;

public class BlackenedEntityRenderer extends MobEntityRenderer<BlackenedEntity, BlackenedEntityModel> {
    public BlackenedEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new BlackenedEntityModel(), 0.3f);
    }

    @Override
    public Identifier getTexture(BlackenedEntity entity) {
        return Desolation.id("textures/entity/blackened.png");
    }
}
