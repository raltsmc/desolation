// Made with Blockbench 3.6.6
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports

	package raltsmc.desolation.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class AshScuttlerEntityModel2 extends EntityModel<AshScuttlerEntity> {
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart leg_left_front;
	private final ModelPart leg_left_middle;
	private final ModelPart leg_left_back;
	private final ModelPart leg_right_front;
	private final ModelPart leg_right_middle;
	private final ModelPart leg_right_back;
	public AshScuttlerEntityModel2() {
		textureWidth = 32;
		textureHeight = 32;
		body = new ModelPart(this);
		body.setPivot(0.0F, 20.7F, 0.0F);
		setRotationAngle(body, -0.0873F, 0.0F, 0.0F);
		body.setTextureOffset(0, 16).addCuboid(0.25F, -0.7538F, 1.5872F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(0, 16).addCuboid(-3.5F, 0.591F, -2.3105F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(4, 16).addCuboid(2.5F, 0.9962F, 1.5872F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(8, 16).addCuboid(1.5F, -1.0038F, -2.6628F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(11, 17).addCuboid(-2.0F, -1.0918F, -1.6241F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(15, 17).addCuboid(-1.5F, 0.2206F, 3.6734F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(0, 0).addCuboid(-3.0F, -0.5038F, -3.9128F, 6.0F, 3.0F, 8.0F, 0.0F, false);

		head = new ModelPart(this);
		head.setPivot(0.0F, 21.4914F, -3.7139F);
		setRotationAngle(head, 0.1309F, 0.0F, 0.0F);
		head.setTextureOffset(0, 11).addCuboid(-2.0F, -1.0F, -2.6667F, 4.0F, 2.0F, 3.0F, 0.0F, false);
		head.setTextureOffset(15, 11).addCuboid(1.25F, -0.5F, -2.9167F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		head.setTextureOffset(0, 6).addCuboid(-2.25F, -0.5F, -2.9167F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		leg_left_front = new ModelPart(this);
		leg_left_front.setPivot(3.0F, 22.0F, -3.75F);
		setRotationAngle(leg_left_front, 0.0F, 0.0F, -0.0873F);
		leg_left_front.setTextureOffset(0, 0).addCuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		leg_left_middle = new ModelPart(this);
		leg_left_middle.setPivot(3.25F, 22.0F, 0.0F);
		setRotationAngle(leg_left_middle, 0.0F, 0.0F, -0.1745F);
		leg_left_middle.setTextureOffset(0, 3).addCuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		leg_left_back = new ModelPart(this);
		leg_left_back.setPivot(3.0F, 22.0F, 3.75F);
		setRotationAngle(leg_left_back, 0.0F, 0.0F, -0.0873F);
		leg_left_back.setTextureOffset(4, 4).addCuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		leg_right_front = new ModelPart(this);
		leg_right_front.setPivot(-3.0F, 22.0F, -3.75F);
		setRotationAngle(leg_right_front, 0.0F, 0.0F, 0.0873F);
		leg_right_front.setTextureOffset(4, 0).addCuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		leg_right_middle = new ModelPart(this);
		leg_right_middle.setPivot(-3.25F, 22.0F, 0.0F);
		setRotationAngle(leg_right_middle, 0.0F, 0.0F, 0.1745F);
		leg_right_middle.setTextureOffset(11, 11).addCuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		leg_right_back = new ModelPart(this);
		leg_right_back.setPivot(-3.0F, 22.0F, 3.75F);
		setRotationAngle(leg_right_back, 0.0F, 0.0F, 0.0873F);
		leg_right_back.setTextureOffset(14, 14).addCuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
	}
	@Override
	public void setAngles(AshScuttlerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}
	@Override
	public void render(MatrixStack matrixStack, VertexConsumer	buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){

		body.render(matrixStack, buffer, packedLight, packedOverlay);
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		leg_left_front.render(matrixStack, buffer, packedLight, packedOverlay);
		leg_left_middle.render(matrixStack, buffer, packedLight, packedOverlay);
		leg_left_back.render(matrixStack, buffer, packedLight, packedOverlay);
		leg_right_front.render(matrixStack, buffer, packedLight, packedOverlay);
		leg_right_middle.render(matrixStack, buffer, packedLight, packedOverlay);
		leg_right_back.render(matrixStack, buffer, packedLight, packedOverlay);
	}
	public void setRotationAngle(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
	}

}