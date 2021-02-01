package raltsmc.desolation.entity.model;

import net.minecraft.util.Identifier;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.entity.AshScuttlerEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AshScuttlerEntityModel extends AnimatedGeoModel<AshScuttlerEntity> {

	@Override
	public Identifier getModelLocation(AshScuttlerEntity object) {
		return Desolation.id("geo/ash_scuttler.geo.json");
	}

	@Override
	public Identifier getTextureLocation(AshScuttlerEntity object) {
		return Desolation.id("textures/entity/ash_scuttler.png");
	}

	@Override
	public Identifier getAnimationFileLocation(AshScuttlerEntity object)
	{
		return Desolation.id("animations/ash_scuttler.json");
	}
}