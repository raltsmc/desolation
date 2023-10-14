package raltsmc.desolation.world.feature;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import raltsmc.desolation.Desolation;

public class DesolationFeatures {
    public static Feature<SingleStateFeatureConfig> GIANT_BOULDER = new GiantBoulderFeature(SingleStateFeatureConfig.CODEC);
    public static Feature<ScatteredFeatureConfig> SCATTERED = new ScatteredFeature(ScatteredFeatureConfig.CODEC);

    public static void init() {
        register("giant_boulder", GIANT_BOULDER);
        register("scattered", SCATTERED);
    }

    private static void register(String name, Feature<?> feature) {
        Registry.register(Registries.FEATURE, Desolation.id(name), feature);
    }
}
