package raltsmc.desolation.world.feature;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import raltsmc.desolation.Desolation;

public class DesolationFeatures {
    public static Feature<SingleStateFeatureConfig> GIANT_BOULDER = new GiantBoulderFeature(SingleStateFeatureConfig.CODEC);

    public static void init() {
        register("giant_boulder", GIANT_BOULDER);
    }

    private static void register(String name, Feature feature) {
        Registry.register(Registry.FEATURE, Desolation.id(name), feature);
    }
}
