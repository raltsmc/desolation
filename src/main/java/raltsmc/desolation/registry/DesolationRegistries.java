package raltsmc.desolation.registry;

import raltsmc.desolation.world.feature.DesolationConfiguredFeatures;
import raltsmc.desolation.world.feature.DesolationFeatures;
import raltsmc.desolation.world.feature.DesolationPlacedFeatures;

public class DesolationRegistries {
    public static void init() {
        DesolationSounds.init();
        DesolationBlocks.init();
        DesolationItems.init();
        DesolationEntities.init();
        DesolationTrunkPlacerTypes.init();
        DesolationFoliagePlacerTypes.init();
        DesolationFeatures.init();
        DesolationConfiguredFeatures.init();
        DesolationPlacedFeatures.init();
        DesolationStructures.init();
        DesolationStatusEffects.init();
        DesolationPotions.init();
        DesolationParticles.init();
        DesolationBiomes.init();
        DesolationItemGroups.init();
    }
}
