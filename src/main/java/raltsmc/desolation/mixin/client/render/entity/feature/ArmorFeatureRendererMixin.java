package raltsmc.desolation.mixin.client.render.entity.feature;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.registry.DesolationItems;

// Mixin from JamiesWhiteShirt/bucket-hat

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>,
        A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    @Shadow protected abstract void setVisible(A bipedModel, EquipmentSlot slot);
    @Shadow protected abstract boolean usesSecondLayer(EquipmentSlot slot);

    private static final Identifier MASK_TEXTURE = Desolation.id("textures/models/armor/mask.png");
    private static final Identifier GOGGLES_TEXTURE = Desolation.id("textures/models/armor/goggles.png");
    private static final Identifier MASK_GOGGLES_TEXTURE = Desolation.id("textures/models/armor/mask_and_goggles.png");

    public ArmorFeatureRendererMixin(FeatureRendererContext<T,M> featureRendererContext_1) {
        super(featureRendererContext_1);
    }

    @Inject(
            method= "renderArmor(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;ILnet/minecraft/client/render/entity/model/BipedEntityModel;)V",
            at = @At("HEAD")
    )
    private void renderArmor(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, T livingEntity,
                             EquipmentSlot equipmentSlot, int i, A armorModel, CallbackInfo ci) {
        if (equipmentSlot == EquipmentSlot.HEAD) {
            ItemStack itemStack = livingEntity.getEquippedStack(equipmentSlot);
            if (itemStack.getItem() == DesolationItems.MASK) {
                getContextModel().setAttributes(armorModel);
                setVisible(armorModel, equipmentSlot);

                boolean renderGlint = itemStack.hasGlint();
                VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getEntityCutoutNoCull(MASK_TEXTURE), false, renderGlint);
                    armorModel.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
            } else if (itemStack.getItem() == DesolationItems.GOGGLES) {
                getContextModel().setAttributes(armorModel);
                setVisible(armorModel, equipmentSlot);

                boolean renderGlint = itemStack.hasGlint();
                VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getEntityCutoutNoCull(GOGGLES_TEXTURE), false, renderGlint);
                armorModel.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
            } else if (itemStack.getItem() == DesolationItems.MASK_GOGGLES) {
                getContextModel().setAttributes(armorModel);
                setVisible(armorModel, equipmentSlot);

                boolean renderGlint = itemStack.hasGlint();
                VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getEntityCutoutNoCull(MASK_GOGGLES_TEXTURE), false, renderGlint);
                armorModel.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
            }
        }
    }
}
