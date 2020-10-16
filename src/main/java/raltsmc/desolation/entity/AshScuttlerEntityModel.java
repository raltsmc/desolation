// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.12.2 or 1.15.2 (same format for both) for entity models animated with GeckoLib
// Paste this class into your mod and follow the documentation for GeckoLib to use animations. You can find the documentation here: https://github.com/bernie-g/geckolib
// Blockbench plugin created by Gecko
package raltsmc.desolation.entity;

import software.bernie.geckolib.forgetofabric.ResourceLocation;
import software.bernie.geckolib.animation.model.AnimatedEntityModel;
import software.bernie.geckolib.animation.render.AnimatedModelRenderer;

public class AshScuttlerEntityModel extends AnimatedEntityModel<AshScuttlerEntity> {

    private final AnimatedModelRenderer body;
	private final AnimatedModelRenderer head;
	private final AnimatedModelRenderer leg_left_front;
	private final AnimatedModelRenderer leg_left_middle;
	private final AnimatedModelRenderer leg_left_back;
	private final AnimatedModelRenderer leg_right_front;
	private final AnimatedModelRenderer leg_right_middle;
	private final AnimatedModelRenderer leg_right_back;

    public AshScuttlerEntityModel()
    {
        textureWidth = 32;
    textureHeight = 32;
    body = new AnimatedModelRenderer(this);
		body.setRotationPoint(0.0F, 20.7F, 0.0F);
		setRotationAngle(body, -0.0873F, 0.0F, 0.0F);
		body.setTextureOffset(0, 16).addBox(0.25F, -0.7538F, 1.5872F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(0, 16).addBox(-3.5F, 0.591F, -2.3105F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(4, 16).addBox(2.5F, 0.9962F, 1.5872F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(8, 16).addBox(1.5F, -1.0038F, -2.6628F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(11, 17).addBox(-2.0F, -1.0918F, -1.6241F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(15, 17).addBox(-1.5F, 0.2206F, 3.6734F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		body.setTextureOffset(0, 0).addBox(-3.0F, -0.5038F, -3.9128F, 6.0F, 3.0F, 8.0F, 0.0F, false);
		body.setModelRendererName("body");
		this.registerModelRenderer(body);

		head = new AnimatedModelRenderer(this);
		head.setRotationPoint(0.0F, 21.4914F, -3.7139F);
		setRotationAngle(head, 0.1309F, 0.0F, 0.0F);
		head.setTextureOffset(0, 11).addBox(-2.0F, -1.0F, -2.6667F, 4.0F, 2.0F, 3.0F, 0.0F, false);
		head.setTextureOffset(15, 11).addBox(1.25F, -0.5F, -2.9167F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		head.setTextureOffset(0, 6).addBox(-2.25F, -0.5F, -2.9167F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		head.setModelRendererName("head");
		this.registerModelRenderer(head);

		leg_left_front = new AnimatedModelRenderer(this);
		leg_left_front.setRotationPoint(3.0F, 22.0F, -3.75F);
		setRotationAngle(leg_left_front, 0.0F, 0.0F, -0.0873F);
		leg_left_front.setTextureOffset(0, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		leg_left_front.setModelRendererName("leg_left_front");
		this.registerModelRenderer(leg_left_front);

		leg_left_middle = new AnimatedModelRenderer(this);
		leg_left_middle.setRotationPoint(3.25F, 22.0F, 0.0F);
		setRotationAngle(leg_left_middle, 0.0F, 0.0F, -0.1745F);
		leg_left_middle.setTextureOffset(0, 3).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		leg_left_middle.setModelRendererName("leg_left_middle");
		this.registerModelRenderer(leg_left_middle);

		leg_left_back = new AnimatedModelRenderer(this);
		leg_left_back.setRotationPoint(3.0F, 22.0F, 3.75F);
		setRotationAngle(leg_left_back, 0.0F, 0.0F, -0.0873F);
		leg_left_back.setTextureOffset(4, 4).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		leg_left_back.setModelRendererName("leg_left_back");
		this.registerModelRenderer(leg_left_back);

		leg_right_front = new AnimatedModelRenderer(this);
		leg_right_front.setRotationPoint(-3.0F, 22.0F, -3.75F);
		setRotationAngle(leg_right_front, 0.0F, 0.0F, 0.0873F);
		leg_right_front.setTextureOffset(4, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		leg_right_front.setModelRendererName("leg_right_front");
		this.registerModelRenderer(leg_right_front);

		leg_right_middle = new AnimatedModelRenderer(this);
		leg_right_middle.setRotationPoint(-3.25F, 22.0F, 0.0F);
		setRotationAngle(leg_right_middle, 0.0F, 0.0F, 0.1745F);
		leg_right_middle.setTextureOffset(11, 11).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		leg_right_middle.setModelRendererName("leg_right_middle");
		this.registerModelRenderer(leg_right_middle);

		leg_right_back = new AnimatedModelRenderer(this);
		leg_right_back.setRotationPoint(-3.0F, 22.0F, 3.75F);
		setRotationAngle(leg_right_back, 0.0F, 0.0F, 0.0873F);
		leg_right_back.setTextureOffset(14, 14).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		leg_right_back.setModelRendererName("leg_right_back");
		this.registerModelRenderer(leg_right_back);

    this.rootBones.add(body);
		this.rootBones.add(head);
		this.rootBones.add(leg_left_front);
		this.rootBones.add(leg_left_middle);
		this.rootBones.add(leg_left_back);
		this.rootBones.add(leg_right_front);
		this.rootBones.add(leg_right_middle);
		this.rootBones.add(leg_right_back);
  }


    @Override
    public ResourceLocation getAnimationFileLocation()
    {
        return new ResourceLocation("desolation", "animations/ash_scuttler.json");
    }
}