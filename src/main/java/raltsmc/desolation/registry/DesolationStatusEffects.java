package raltsmc.desolation.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.entity.effect.CinderSoulStatusEffect;

public class DesolationStatusEffects {
    public static final StatusEffect CINDER_SOUL= register("cinder_soul", new CinderSoulStatusEffect());

    private static StatusEffect register(String name, StatusEffect entry) {
        return Registry.register(Registries.STATUS_EFFECT, Desolation.id(name), entry);
    }

    public static void init() { }
}
