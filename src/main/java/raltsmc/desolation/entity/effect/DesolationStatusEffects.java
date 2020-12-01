package raltsmc.desolation.entity.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.registry.Registry;
import raltsmc.desolation.Desolation;

public class DesolationStatusEffects {
    public static final StatusEffect CINDER_SOUL;

    private static StatusEffect register(String id, StatusEffect entry) {
        return (StatusEffect)Registry.register(Registry.STATUS_EFFECT, Desolation.id(id), entry);
    }

    public static void init() {
    }

    static {
        CINDER_SOUL = register("cinder_soul", (new CinderSoulStatusEffect()));
    }
}
