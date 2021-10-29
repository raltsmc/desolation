package raltsmc.desolation.registry;

import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import raltsmc.desolation.entity.effect.DesolationStatusEffects;
import raltsmc.desolation.item.potion.DesolationPotions;
import raltsmc.desolation.mixin.recipe.BrewingRecipeRegistryInvoker;
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
        DesolationStatusEffects.init();
        DesolationPotions.init();
        DesolationParticles.init();

        BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(Potions.AWKWARD, DesolationItems.INFUSED_POWDER, Potions.FIRE_RESISTANCE);
        BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(Potions.AWKWARD, DesolationItems.HEART_OF_CINDER, DesolationPotions.CINDER_SOUL);
        BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(DesolationPotions.CINDER_SOUL, Items.REDSTONE, DesolationPotions.LONG_CINDER_SOUL);
        BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(Potions.AWKWARD, DesolationItems.PRIMED_ASH, DesolationPotions.BLINDNESS);
        BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(DesolationPotions.BLINDNESS, Items.REDSTONE, DesolationPotions.LONG_BLINDNESS);
    }
}
