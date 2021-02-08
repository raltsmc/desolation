package raltsmc.desolation.client.model;

import net.minecraft.util.Identifier;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.entity.AshFlierEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AshFlierEntityModel extends AnimatedGeoModel<AshFlierEntity> {

	@Override
	public Identifier getModelLocation(AshFlierEntity object) {
		return Desolation.id("geo/ash_flier.geo.json");
	}

	@Override
	public Identifier getTextureLocation(AshFlierEntity object) {
		return Desolation.id("textures/entity/ash_flier.png");
	}

	@Override
	public Identifier getAnimationFileLocation(AshFlierEntity object)
	{
		return Desolation.id("animations/ash_flier.animation.json");
	}
}