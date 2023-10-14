package raltsmc.desolation.registry;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import raltsmc.desolation.Desolation;

public class DesolationPotions {
    public static final Potion CINDER_SOUL = register("cinder_soul", new Potion(new StatusEffectInstance(DesolationStatusEffects.CINDER_SOUL, 1200)));
    public static final Potion LONG_CINDER_SOUL = register("long_cinder_soul", new Potion("cinder_soul", new StatusEffectInstance(DesolationStatusEffects.CINDER_SOUL, 3600)));
    public static final Potion BLINDNESS = register("blindness", new Potion(new StatusEffectInstance(StatusEffects.BLINDNESS, 1200)));
    public static final Potion LONG_BLINDNESS = register("long_blindness", new Potion("blindness", new StatusEffectInstance(StatusEffects.BLINDNESS, 3600)));

    private static Potion register(String id, Potion potion) {
        return Registry.register(Registries.POTION, Desolation.id(id), potion);
    }

    public static void init() {
        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, DesolationItems.INFUSED_POWDER, Potions.FIRE_RESISTANCE);
        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, DesolationItems.HEART_OF_CINDER, DesolationPotions.CINDER_SOUL);
        BrewingRecipeRegistry.registerPotionRecipe(DesolationPotions.CINDER_SOUL, Items.REDSTONE, DesolationPotions.LONG_CINDER_SOUL);
        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, DesolationItems.PRIMED_ASH, DesolationPotions.BLINDNESS);
        BrewingRecipeRegistry.registerPotionRecipe(DesolationPotions.BLINDNESS, Items.REDSTONE, DesolationPotions.LONG_BLINDNESS);
    }
}
