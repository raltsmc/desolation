package raltsmc.desolation.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class MaskEntityModel<T extends LivingEntity> extends AnimalModel<T> {
    protected final ModelPart gear;

    public MaskEntityModel(ModelPart head) {
        this.gear = head.getChild("mask");
    }

    public static ModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
//        modelPartData.addChild("mask", ModelPartBuilder.create().uv(0, 13).cuboid(-0.5F, 0F, -0.2F, 9F, 4F, 8F),
//                ModelTransform.of(4F, -1F, 4F, -10F, 0F, 0F));
//        modelPartData.addChild("mask", ModelPartBuilder.create().uv(0, 13).cuboid(-0.5F*8F, 0F, -0.2F*8F, 9F, 4F, 8F),
//                ModelTransform.of(4F*8F, -1F*8F, 4F*8F, -10F, 0F, 0F));
//        modelPartData.addChild("mask", ModelPartBuilder.create().uv(0, 13).cuboid(.5F, 0F, .2F, 9F, 4F, 8F),
//                ModelTransform.NONE);
//        modelPartData.addChild("mask", ModelPartBuilder.create().uv(0, 13).cuboid(-4.5F, -4F, -5F, 9F, 4F, 8F),
//                //ModelTransform.NONE);
//                ModelTransform.pivot(0F, -4F, 0F));
//        return modelData;

//        modelPartData.addChild("mask", ModelPartBuilder.create().uv(0, 13).cuboid(-4.5F, -5F, -5F, 9F, 4F, 8F),
//                //ModelTransform.NONE);
//                ModelTransform.pivot(0F, -5F, -2F));

        modelPartData.addChild("mask", ModelPartBuilder.create().uv(0, 13).cuboid(-4.5F, -6F, -3F, 9F, 4F, 8F),
                ModelTransform.NONE);
        return modelData;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.gear);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of();
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.setHeadRotation(headYaw, headPitch + 10F);
    }

    public void setHeadRotation(float yaw, float pitch) {
        this.gear.yaw = yaw * 0.017453292F;
        this.gear.pitch = pitch * 0.017453292F;
    }
}
