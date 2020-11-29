package raltsmc.desolation.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.registry.DesolationItems;

@Environment(EnvType.CLIENT)
public class HeadGearFeatureRenderer<T extends LivingEntity, A extends BipedEntityModel<T>> extends FeatureRenderer<T, A> {
    private final A bodyModel;

    private static final Identifier MASK_TEXTURE = Desolation.id("textures/models/armor/headgear_mask_layer_1.png");
    private static final Identifier GOGGLES_TEXTURE = Desolation.id("textures/models/armor/headgear_goggles_layer_1.png");
    private static final Identifier MASK_GOGGLES_TEXTURE = Desolation.id("textures/models/armor/headgear_mask_and_goggles_layer_1.png");

    public HeadGearFeatureRenderer(FeatureRendererContext<T, A> context, A bodyModel) {
        super(context);
        this.bodyModel = bodyModel;
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.HEAD);
        if (itemStack.getItem() == DesolationItems.MASK) {
            getContextModel().setAttributes(this.bodyModel);
            this.bodyModel.setVisible(false);
            this.bodyModel.head.visible = true;
            boolean renderGlint = itemStack.hasGlint();
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(MASK_TEXTURE), false, renderGlint);
            this.bodyModel.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
        } else if (itemStack.getItem() == DesolationItems.GOGGLES) {
            getContextModel().setAttributes(this.bodyModel);
            this.bodyModel.setVisible(false);
            this.bodyModel.head.visible = true;
            boolean renderGlint = itemStack.hasGlint();
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(GOGGLES_TEXTURE), false, renderGlint);
            this.bodyModel.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
        } else if (itemStack.getItem() == DesolationItems.MASK_GOGGLES) {
            getContextModel().setAttributes(this.bodyModel);
            this.bodyModel.setVisible(false);
            this.bodyModel.head.visible = true;
            boolean renderGlint = itemStack.hasGlint();
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(MASK_GOGGLES_TEXTURE), false, renderGlint);
            this.bodyModel.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
        }
    }
}
