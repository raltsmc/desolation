package raltsmc.desolation.registry;

import net.minecraft.potion.Potions;
import raltsmc.desolation.mixin.BrewingRecipeRegistryInvoker;
import raltsmc.desolation.world.feature.DesolationConfiguredFeatures;
import raltsmc.desolation.world.feature.DesolationFeatures;

public class DesolationRegistries {

    public static void init() {
        DesolationItems.init();
        DesolationBlocks.init();
        DesolationConfiguredFeatures.init();
        DesolationEntities.init();
        DesolationTrunkPlacerTypes.init();
        DesolationFoliagePlacerTypes.init();
        DesolationFeatures.init();

        BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(Potions.AWKWARD, DesolationItems.INFUSED_POWDER,
                Potions.FIRE_RESISTANCE);
    }
}
