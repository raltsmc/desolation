package raltsmc.desolation.client.render.entity.feature;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import raltsmc.desolation.Desolation;

public class HeadTrinketRenderer implements TrinketRenderer {
    private final Identifier texture;
    private final BipedEntityModel<LivingEntity> model;

    public HeadTrinketRenderer(String path, BipedEntityModel<LivingEntity> model) {
        this(Desolation.id(path), model);
    }

    public HeadTrinketRenderer(Identifier texture, BipedEntityModel<LivingEntity> model) {
        this.texture = texture;
        this.model = model;
    }

    protected Identifier getTexture() {
        return texture;
    }

    protected BipedEntityModel<LivingEntity> getModel() {
        return model;
    }

    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel,
                       MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity,
                       float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw,
                       float headPitch) {
        BipedEntityModel<LivingEntity> model = getModel();

        model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        model.animateModel(entity, limbAngle, limbDistance, tickDelta);
        TrinketRenderer.followBodyRotations(entity, model);
        render(matrices, vertexConsumers, light, stack.hasGlint());
    }

    protected void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, boolean hasGlint) {
        RenderLayer layer = model.getLayer(getTexture());
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, layer, false, hasGlint);
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
    }
}
