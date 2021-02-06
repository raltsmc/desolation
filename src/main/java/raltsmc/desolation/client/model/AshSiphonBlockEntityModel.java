package raltsmc.desolation.client.model;

import net.minecraft.util.Identifier;
import raltsmc.desolation.Desolation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AshSiphonBlockEntityModel extends AnimatedGeoModel {
    @Override
    public Identifier getModelLocation(Object entity) {
        return Desolation.id("geo/ash_siphon.geo.json");
    }

    @Override
    public Identifier getTextureLocation(Object animatable) {
        return Desolation.id("textures/block/entity/ash_siphon.png");
    }

    @Override
    public Identifier getAnimationFileLocation(Object entity) {
        return Desolation.id("animations/ash_siphon.animation.json");
    }
}
