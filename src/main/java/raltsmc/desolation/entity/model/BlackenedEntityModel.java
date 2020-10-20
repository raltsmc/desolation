package raltsmc.desolation.entity.model;

import raltsmc.desolation.entity.BlackenedEntity;
import software.bernie.geckolib.forgetofabric.ResourceLocation;
import software.bernie.geckolib.animation.model.AnimatedEntityModel;
import software.bernie.geckolib.animation.render.AnimatedModelRenderer;

public class BlackenedEntityModel extends AnimatedEntityModel<BlackenedEntity> {

    private final AnimatedModelRenderer body;
	private final AnimatedModelRenderer body_upper;
	private final AnimatedModelRenderer head;
	private final AnimatedModelRenderer lower_head;
	private final AnimatedModelRenderer upper_head;
	private final AnimatedModelRenderer torso;
	private final AnimatedModelRenderer ribcage;
	private final AnimatedModelRenderer arm_left;
	private final AnimatedModelRenderer arm_right;
	private final AnimatedModelRenderer leg_left;
	private final AnimatedModelRenderer leg_right;

    public BlackenedEntityModel()
    {
        textureWidth = 64;
    textureHeight = 64;
    body = new AnimatedModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		
		body.setModelRendererName("body");
		this.registerModelRenderer(body);

		body_upper = new AnimatedModelRenderer(this);
		body_upper.setRotationPoint(0.0F, -12.0F, 0.0F);
		body.addChild(body_upper);
		
		body_upper.setModelRendererName("body_upper");
		this.registerModelRenderer(body_upper);

		head = new AnimatedModelRenderer(this);
		head.setRotationPoint(0.0F, -12.0F, 0.0F);
		body_upper.addChild(head);
		
		head.setModelRendererName("head");
		this.registerModelRenderer(head);

		lower_head = new AnimatedModelRenderer(this);
		lower_head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(lower_head);
		lower_head.setTextureOffset(0, 15).addBox(-4.0F, -1.0F, -3.0F, 8.0F, 1.0F, 7.0F, 0.0F, false);
		lower_head.setModelRendererName("lower_head");
		this.registerModelRenderer(lower_head);

		upper_head = new AnimatedModelRenderer(this);
		upper_head.setRotationPoint(0.0F, -1.0F, 3.0F);
		head.addChild(upper_head);
		upper_head.setTextureOffset(0, 0).addBox(-4.0F, -7.0F, -7.0F, 8.0F, 7.0F, 8.0F, 0.0F, false);
		upper_head.setModelRendererName("upper_head");
		this.registerModelRenderer(upper_head);

		torso = new AnimatedModelRenderer(this);
		torso.setRotationPoint(0.0F, 0.0F, 0.0F);
		body_upper.addChild(torso);
		torso.setTextureOffset(24, 0).addBox(-2.0F, -10.0F, -1.0F, 4.0F, 3.0F, 2.0F, 0.0F, false);
		torso.setTextureOffset(32, 32).addBox(-1.0F, -12.0F, 0.0F, 2.0F, 10.0F, 2.0F, 0.0F, false);
		torso.setTextureOffset(0, 24).addBox(-4.0F, -2.0F, -2.0F, 8.0F, 2.0F, 4.0F, 0.0F, false);
		torso.setModelRendererName("torso");
		this.registerModelRenderer(torso);

		ribcage = new AnimatedModelRenderer(this);
		ribcage.setRotationPoint(0.0F, -6.0F, 1.0F);
		torso.addChild(ribcage);
		ribcage.setTextureOffset(24, 24).addBox(-4.0F, -6.0F, -3.0F, 8.0F, 6.0F, 1.0F, 0.0F, false);
		ribcage.setTextureOffset(0, 44).addBox(-4.0F, -6.0F, 0.0F, 8.0F, 6.0F, 1.0F, 0.0F, false);
		ribcage.setTextureOffset(33, 2).addBox(3.0F, -6.0F, -2.0F, 1.0F, 6.0F, 3.0F, 0.0F, false);
		ribcage.setTextureOffset(29, 12).addBox(-4.0F, -6.0F, -2.0F, 1.0F, 6.0F, 3.0F, 0.0F, false);
		ribcage.setModelRendererName("ribcage");
		this.registerModelRenderer(ribcage);

		arm_left = new AnimatedModelRenderer(this);
		arm_left.setRotationPoint(5.0F, -11.0F, 0.0F);
		body_upper.addChild(arm_left);
		arm_left.setTextureOffset(8, 30).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		arm_left.setModelRendererName("arm_left");
		this.registerModelRenderer(arm_left);

		arm_right = new AnimatedModelRenderer(this);
		arm_right.setRotationPoint(-5.0F, -11.0F, 0.0F);
		body_upper.addChild(arm_right);
		arm_right.setTextureOffset(0, 30).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		arm_right.setModelRendererName("arm_right");
		this.registerModelRenderer(arm_right);

		leg_left = new AnimatedModelRenderer(this);
		leg_left.setRotationPoint(2.0F, -12.0F, 0.0F);
		body.addChild(leg_left);
		leg_left.setTextureOffset(24, 31).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		leg_left.setModelRendererName("leg_left");
		this.registerModelRenderer(leg_left);

		leg_right = new AnimatedModelRenderer(this);
		leg_right.setRotationPoint(-2.0F, -12.0F, 0.0F);
		body.addChild(leg_right);
		leg_right.setTextureOffset(16, 30).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);
		leg_right.setModelRendererName("leg_right");
		this.registerModelRenderer(leg_right);

    this.rootBones.add(body);
  }


    @Override
    public ResourceLocation getAnimationFileLocation()
    {
        return new ResourceLocation("desolation", "animations/blackened.json");
    }
}