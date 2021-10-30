package raltsmc.desolation.client.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.registry.DesolationItems;

@Environment(EnvType.CLIENT)
public abstract class HeadGearFeatureRenderer<T extends Entity, A extends EntityModel<T> & ModelWithHead> extends FeatureRenderer<T, A> {
    public static final Identifier SKIN = Desolation.id("textures/entity/ash_tinkerer_headgear.png");

    public HeadGearFeatureRenderer(FeatureRendererContext<T, A> context) {
        super(context);
    }

    public abstract void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l);
}
