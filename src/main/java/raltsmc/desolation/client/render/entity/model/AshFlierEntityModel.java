/*package raltsmc.desolation;

import software.bernie.geckolib.forgetofabric.ResourceLocation;
import software.bernie.geckolib.animation.model.AnimatedEntityModel;
import software.bernie.geckolib.animation.render.AnimatedModelRenderer;

public class AshFlierEntityModel extends AnimatedEntityModel<AshFlierEntity> {

    private final AnimatedModelRenderer main;
	private final AnimatedModelRenderer wing_left;
	private final AnimatedModelRenderer head;
	private final AnimatedModelRenderer head_antennae;
	private final AnimatedModelRenderer wing_right;
	private final AnimatedModelRenderer body;
	private final AnimatedModelRenderer body_lower;
	private final AnimatedModelRenderer spine;
	private final AnimatedModelRenderer tail;

    public AshFlierEntityModel()
    {
        textureWidth = 64;
    textureHeight = 64;
    main = new AnimatedModelRenderer(this);
		main.setRotationPoint(0.0F, 24.0F, 0.0F);
		
		main.setModelRendererName("main");
		this.registerModelRenderer(main);

		wing_left = new AnimatedModelRenderer(this);
		wing_left.setRotationPoint(0.5F, -2.3F, 0.0F);
		main.addChild(wing_left);
		setRotationAngle(wing_left, 0.0F, 0.0F, -0.0087F);
		wing_left.setTextureOffset(31, 9).addBox(3.8873F, -0.0909F, -4.0F, 3.0F, 1.0F, 8.0F, 0.0F, false);
		wing_left.setTextureOffset(22, 25).addBox(-0.1127F, -0.0909F, -4.0F, 4.0F, 1.0F, 8.0F, 0.0F, false);
		wing_left.setTextureOffset(18, 34).addBox(-0.3868F, -0.173F, -2.0F, 8.0F, 1.0F, 4.0F, 0.0F, false);
		wing_left.setModelRendererName("wing_left");
		this.registerModelRenderer(wing_left);

		head = new AnimatedModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		main.addChild(head);
		head.setTextureOffset(14, 26).addBox(-2.5F, -2.8F, -7.7F, 5.0F, 3.0F, 2.0F, 0.0F, false);
		head.setModelRendererName("head");
		this.registerModelRenderer(head);

		head_antennae = new AnimatedModelRenderer(this);
		head_antennae.setRotationPoint(0.25F, 0.95F, -8.3F);
		head.addChild(head_antennae);
		setRotationAngle(head_antennae, -1.6581F, 0.0F, 0.0F);
		head_antennae.setTextureOffset(0, 5).addBox(-3.0F, -1.0F, -5.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
		head_antennae.setTextureOffset(0, 0).addBox(1.5F, -1.0F, -5.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
		head_antennae.setModelRendererName("head_antennae");
		this.registerModelRenderer(head_antennae);

		wing_right = new AnimatedModelRenderer(this);
		wing_right.setRotationPoint(-0.5F, -2.3F, 0.0F);
		main.addChild(wing_right);
		setRotationAngle(wing_right, 0.0F, 0.0F, 0.0087F);
		wing_right.setTextureOffset(0, 26).addBox(-6.8873F, -0.0909F, -4.0F, 3.0F, 1.0F, 8.0F, 0.0F, false);
		wing_right.setTextureOffset(20, 0).addBox(-3.8873F, -0.0909F, -4.0F, 4.0F, 1.0F, 8.0F, 0.0F, false);
		wing_right.setTextureOffset(31, 18).addBox(-7.6132F, -0.173F, -2.0F, 8.0F, 1.0F, 4.0F, 0.0F, false);
		wing_right.setModelRendererName("wing_right");
		this.registerModelRenderer(wing_right);

		body = new AnimatedModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		main.addChild(body);
		body.setTextureOffset(0, 0).addBox(-2.0F, -2.0F, -6.0F, 4.0F, 1.0F, 12.0F, 0.0F, false);
		body.setModelRendererName("body");
		this.registerModelRenderer(body);

		body_lower = new AnimatedModelRenderer(this);
		body_lower.setRotationPoint(2.5F, 0.3F, 6.0F);
		body.addChild(body_lower);
		setRotationAngle(body_lower, 0.0524F, 0.0F, 0.0F);
		body_lower.setTextureOffset(0, 13).addBox(-4.0F, -2.0F, -12.0F, 3.0F, 1.0F, 12.0F, 0.0F, false);
		body_lower.setModelRendererName("body_lower");
		this.registerModelRenderer(body_lower);

		spine = new AnimatedModelRenderer(this);
		spine.setRotationPoint(0.5F, -1.1F, -8.0F);
		body.addChild(spine);
		setRotationAngle(spine, -0.0524F, 0.0F, 0.0F);
		spine.setTextureOffset(18, 13).addBox(-1.0F, -2.0F, 1.0F, 1.0F, 1.0F, 11.0F, 0.0F, false);
		spine.setModelRendererName("spine");
		this.registerModelRenderer(spine);

		tail = new AnimatedModelRenderer(this);
		tail.setRotationPoint(0.0F, -1.4F, 5.2F);
		main.addChild(tail);
		tail.setTextureOffset(36, 0).addBox(-3.0F, -1.05F, 0.05F, 6.0F, 1.0F, 3.0F, 0.0F, false);
		tail.setTextureOffset(0, 35).addBox(-3.0F, 0.15F, 0.05F, 6.0F, 1.0F, 3.0F, 0.0F, false);
		tail.setModelRendererName("tail");
		this.registerModelRenderer(tail);

    this.rootBones.add(main);
  }


    @Override
    public ResourceLocation getAnimationFileLocation()
    {
        return new ResourceLocation("desolation", "animations/ANIMATIONFILE.json");
    }
}*/