package raltsmc.desolation.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class GogglesEntityModel<T extends LivingEntity> extends AnimalModel<T> {
    protected final ModelPart gear;

    public GogglesEntityModel(ModelPart head) {
        this.gear = head.getChild("goggles");
    }

    public static ModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
//        modelPartData.addChild("goggles", ModelPartBuilder.create().uv(0, 0).cuboid(-1F, 3F, -0.7F, 10F, 4F, 9F),
//                ModelTransform.of(4F, 0F, 4F, 2.5F, 0F, 0F));
//        modelPartData.addChild("goggles", ModelPartBuilder.create().uv(0, 0).cuboid(-1F*8F, 3F*8F, -0.7F*8F, 10F, 4F, 9F),
//                ModelTransform.of(4F*8F, 0F, 4F*8F, 2.5F, 0F, 0F));
//        modelPartData.addChild("goggles", ModelPartBuilder.create().uv(0, 0).cuboid(1F, -3F, .7F, 10F, 4F, 9F),
//                ModelTransform.NONE);
//        modelPartData.addChild("goggles", ModelPartBuilder.create().uv(0, 0).cuboid(-5F, -7F, -6F, 10F, 4F, 9F),
//                //ModelTransform.NONE);
//                ModelTransform.pivot(0F, -4F, 0F));

//        modelPartData.addChild("goggles", ModelPartBuilder.create().uv(0, 0).cuboid(-5F, -7F, -6F, 10F, 4F, 9F),
//                //ModelTransform.NONE);
//                ModelTransform.pivot(0F, -5F, -2F));
        modelPartData.addChild("goggles", ModelPartBuilder.create().uv(0, 0).cuboid(-5F, -8F, -5F, 10F, 4F, 9F),
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
        this.setHeadRotation(headYaw, headPitch - 2.5F);
    }

    public void setHeadRotation(float yaw, float pitch) {
        this.gear.yaw = yaw * 0.017453292F;
        this.gear.pitch = pitch * 0.017453292F;
    }
}
