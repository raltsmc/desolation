package raltsmc.desolation.registry;

import net.minecraft.potion.Potions;
import raltsmc.desolation.mixin.BrewingRecipeRegistryInvoker;

public class DesolationRegistries {

    public static void init() {
        DesolationItems.init();
        DesolationBlocks.init();
        DesolationStructures.init();
        DesolationEntities.init();

        BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(Potions.AWKWARD, DesolationItems.INFUSED_POWDER,
                Potions.FIRE_RESISTANCE);
    }
}
