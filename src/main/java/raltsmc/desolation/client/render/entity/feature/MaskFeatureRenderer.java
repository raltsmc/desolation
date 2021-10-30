package raltsmc.desolation.client.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.client.render.entity.model.MaskEntityModel;
import raltsmc.desolation.init.client.DesolationClient;
import raltsmc.desolation.registry.DesolationItems;

@Environment(EnvType.CLIENT)
public class MaskFeatureRenderer<T extends LivingEntity, A extends EntityModel<T> & ModelWithHead> extends HeadGearFeatureRenderer<T, A> {
    private final MaskEntityModel<T> mask;

    public MaskFeatureRenderer(FeatureRendererContext<T, A> context, EntityModelLoader loader) {
        super(context);
        this.mask = new MaskEntityModel<>(loader.getModelPart(DesolationClient.HEAD_MASK_LAYER));
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.HEAD);
        if (itemStack.getItem() == DesolationItems.MASK || itemStack.getItem() == DesolationItems.MASK_GOGGLES) {
            //matrixStack.push();
            this.getContextModel().copyStateTo(mask);
            this.mask.setAngles(livingEntity, f, g, j, k, l);
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider,
                    RenderLayer.getEntityTranslucent(SKIN), false, itemStack.hasGlint());
            this.mask.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1F, 1F, 1F, 1F);
            //matrixStack.pop();
        }
    }
}
