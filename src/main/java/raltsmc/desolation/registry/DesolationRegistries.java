package raltsmc.desolation.registry;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import raltsmc.desolation.config.DesolationConfig;
import raltsmc.desolation.entity.effect.DesolationStatusEffects;
import raltsmc.desolation.item.potion.DesolationPotions;
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
        DesolationStatusEffects.init();
        DesolationPotions.init();

        BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(Potions.AWKWARD, DesolationItems.INFUSED_POWDER, Potions.FIRE_RESISTANCE);
        BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(Potions.AWKWARD, DesolationItems.HEART_OF_CINDER, DesolationPotions.CINDER_SOUL);
        BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(DesolationPotions.CINDER_SOUL, Items.REDSTONE, DesolationPotions.LONG_CINDER_SOUL);
        BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(Potions.AWKWARD, DesolationItems.PRIMED_ASH, DesolationPotions.BLINDNESS);
        BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(DesolationPotions.BLINDNESS, Items.REDSTONE, DesolationPotions.LONG_BLINDNESS);

        AutoConfig.register(DesolationConfig.class, JanksonConfigSerializer::new);
    }
}
