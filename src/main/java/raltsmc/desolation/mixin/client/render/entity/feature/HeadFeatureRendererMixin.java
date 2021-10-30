package raltsmc.desolation.mixin.client.render.entity.feature;

import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import raltsmc.desolation.client.render.entity.feature.GogglesFeatureRenderer;
import raltsmc.desolation.client.render.entity.feature.MaskFeatureRenderer;
import raltsmc.desolation.client.render.entity.model.MaskEntityModel;
import raltsmc.desolation.registry.DesolationItems;

// Mixin from JamiesWhiteShirt/bucket-hat

@Mixin(HeadFeatureRenderer.class)
public abstract class HeadFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T> & ModelWithHead> extends FeatureRenderer<T, M> {
    private FeatureRendererContext<T, M> context;
    private EntityModelLoader loader;

    public HeadFeatureRendererMixin(FeatureRendererContext<T, M> featureRendererContext_1) {
        super(featureRendererContext_1);
    }

    @Inject(
            method = "<init>(Lnet/minecraft/client/render/entity/feature/FeatureRendererContext;" +
                    "Lnet/minecraft/client/render/entity/model/EntityModelLoader;FFF)V",
            at = @At("TAIL")
    )
    public void constructorHead(FeatureRendererContext<T, M> context, EntityModelLoader loader, float scaleX,
                                float scaleY, float scaleZ, CallbackInfo ci) {
        this.context = context;
        this.loader = loader;
    }

    @Inject(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/ModelPart;rotate(Lnet/minecraft/client/util/math/MatrixStack;)V",
                    ordinal = 0
            ),
            cancellable = true
    )
    private void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity,
                        float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
//        if (stack.getItem() == DesolationItems.MASK || stack.getItem() == DesolationItems.GOGGLES || stack.getItem() == DesolationItems.MASK_GOGGLES) {
//            ci.cancel();
//        }
        ItemStack stack = livingEntity.getEquippedStack(EquipmentSlot.HEAD);
        boolean hasMask = (stack.getItem() == DesolationItems.MASK || stack.getItem() == DesolationItems.MASK_GOGGLES);
        boolean hasGoggles = (stack.getItem() == DesolationItems.GOGGLES || stack.getItem() == DesolationItems.MASK_GOGGLES);
        if (hasMask) {
            MaskFeatureRenderer<T, M> mask = new MaskFeatureRenderer<>(context, loader);
            mask.render(matrixStack, vertexConsumerProvider, i, livingEntity, f, g, h, j, k, l);
        }
        if (hasGoggles) {
            GogglesFeatureRenderer<T, M> goggles = new GogglesFeatureRenderer<>(context, loader);
            goggles.render(matrixStack, vertexConsumerProvider, i, livingEntity, f, g, h, j, k, l);
        }
        if (hasMask || hasGoggles) {
            matrixStack.pop();
            ci.cancel();
        }
    }
}
