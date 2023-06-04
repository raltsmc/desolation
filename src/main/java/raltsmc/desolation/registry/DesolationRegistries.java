package raltsmc.desolation.registry;

import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import raltsmc.desolation.mixin.recipe.BrewingRecipeRegistryInvoker;
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

        BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(Potions.AWKWARD, DesolationItems.INFUSED_POWDER, Potions.FIRE_RESISTANCE);
        BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(Potions.AWKWARD, DesolationItems.HEART_OF_CINDER, DesolationPotions.CINDER_SOUL);
        BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(DesolationPotions.CINDER_SOUL, Items.REDSTONE, DesolationPotions.LONG_CINDER_SOUL);
        BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(Potions.AWKWARD, DesolationItems.PRIMED_ASH, DesolationPotions.BLINDNESS);
        BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(DesolationPotions.BLINDNESS, Items.REDSTONE, DesolationPotions.LONG_BLINDNESS);
    }
}
