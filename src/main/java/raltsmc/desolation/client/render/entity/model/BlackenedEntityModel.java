package raltsmc.desolation.client.render.entity.model;

import raltsmc.desolation.Desolation;
import raltsmc.desolation.entity.BlackenedEntity;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class BlackenedEntityModel extends DefaultedEntityGeoModel<BlackenedEntity> {
	public BlackenedEntityModel() {
		super(Desolation.id("blackened"), true);
	}

	@Override
    @SuppressWarnings("unchecked")
	public void setCustomAnimations(BlackenedEntity entity, long uniqueID, AnimationState customPredicate) {
    	super.setCustomAnimations(entity, uniqueID, customPredicate);
		CoreGeoBone head = this.getAnimationProcessor().getBone("head");
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraData().get(EntityModelData.class);

		// TODO: For some reason, extraData is sometimes? null ... need to track this down
		if (head != null && extraData != null) {
			head.setRotX(extraData.headPitch() * ((float) Math.PI / 360F));
			head.setRotY(extraData.netHeadYaw() * ((float) Math.PI / 340F));
		}
	}
}