package raltsmc.desolation.client.model;

import net.minecraft.util.Identifier;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.entity.BlackenedEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BlackenedEntityModel extends AnimatedGeoModel<BlackenedEntity> {

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
}