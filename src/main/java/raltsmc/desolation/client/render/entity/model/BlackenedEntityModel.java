package raltsmc.desolation.client.render.entity.model;

import net.minecraft.util.Identifier;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.entity.BlackenedEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BlackenedEntityModel<T> extends AnimatedGeoModel<BlackenedEntity> {

    @Override
	public Identifier getModelLocation(BlackenedEntity object) {
    	return Desolation.id("geo/blackened.geo.json");
	}

	@Override
	public Identifier getTextureLocation(BlackenedEntity object) {
    	return Desolation.id("textures/entity/blackened.png");
	}

    @Override
    public Identifier getAnimationFileLocation(BlackenedEntity object)
    {
        return Desolation.id("animations/blackened.json");
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(BlackenedEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
    	super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(extraData.headPitch * ((float) Math.PI / 360F));
			head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 340F));
		}
	}
}