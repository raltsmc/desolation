package raltsmc.desolation.registry;

import net.minecraft.potion.Potions;
import raltsmc.desolation.mixin.BrewingRecipeRegistryInvoker;
import raltsmc.desolation.world.feature.DesolationFeatures;

public class DesolationRegistries {

    public static void init() {
        DesolationItems.init();
        DesolationBlocks.init();
        DesolationStructures.init();
        DesolationEntities.init();
        DesolationTrunkPlacerTypes.init();
        DesolationFeatures.init();

        BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(Potions.AWKWARD, DesolationItems.INFUSED_POWDER,
                Potions.FIRE_RESISTANCE);
    }
}
