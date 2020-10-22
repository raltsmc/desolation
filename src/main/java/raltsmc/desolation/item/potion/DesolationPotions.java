package raltsmc.desolation.item.potion;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.util.registry.Registry;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.entity.effect.DesolationStatusEffects;

public class DesolationPotions {
    public static final Potion CINDER_SOUL;
    public static final Potion LONG_CINDER_SOUL;
    public static final Potion BLINDNESS;
    public static final Potion LONG_BLINDNESS;

    private static Potion register(String id, Potion potion) {
        return (Potion) Registry.register(Registry.POTION, Desolation.id(id), potion);
        //return (Potion) Registry.register(Registry.POTION, name, potion);
    }

    public static void init() {

    }

    static {
        CINDER_SOUL = register("cinder_soul", new Potion(new StatusEffectInstance[]{new StatusEffectInstance(DesolationStatusEffects.CINDER_SOUL, 1200)}));
        LONG_CINDER_SOUL = register("long_cinder_soul", new Potion("cinder_soul", new StatusEffectInstance[]{new StatusEffectInstance(DesolationStatusEffects.CINDER_SOUL, 3600)}));
        BLINDNESS = register("blindness", new Potion(new StatusEffectInstance[]{new StatusEffectInstance(StatusEffects.BLINDNESS, 1200)}));
        LONG_BLINDNESS = register("long_blindness", new Potion("blindness", new StatusEffectInstance[]{new StatusEffectInstance(StatusEffects.BLINDNESS, 3600)}));
    }
}
