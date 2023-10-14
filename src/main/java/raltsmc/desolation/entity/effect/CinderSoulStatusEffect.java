package raltsmc.desolation.entity.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class CinderSoulStatusEffect extends StatusEffect {
    public CinderSoulStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xff5900);
        this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "D25F6081-9C1C-4126-B955-A6A4514A8BAE", 7.0D, EntityAttributeModifier.Operation.ADDITION)
                .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, "2027FBEE-2B20-4364-9D78-52FF907B7A71", 0.5D, EntityAttributeModifier.Operation.ADDITION)
                .addAttributeModifier(EntityAttributes.GENERIC_ARMOR, "B17930CF-00C2-44F4-B3BB-368C59AD3A91", 4.0D, EntityAttributeModifier.Operation.ADDITION)
                .addAttributeModifier(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, "BA06E012-463B-42BC-8BD8-55363A540163", 1.5D, EntityAttributeModifier.Operation.ADDITION);
    }
}
