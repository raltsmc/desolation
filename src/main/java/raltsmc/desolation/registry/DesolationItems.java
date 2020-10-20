package raltsmc.desolation.registry;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.DesolationMod;
import raltsmc.desolation.misc.MaskMaterial;
import raltsmc.desolation.item.*;

public final class DesolationItems {

    public static final Item CHARCOAL_BIT = register(new Item(new Item.Settings().group(DesolationMod.DSL_GROUP)), "charcoal_bit");
    public static final Item ASH_PILE = register(new AshItem(new Item.Settings().group(DesolationMod.DSL_GROUP)), "ash_pile");
    public static final Item ACTIVATED_CHARCOAL = register(new Item(new Item.Settings().group(DesolationMod.DSL_GROUP)), "activated_charcoal");
    public static final Item PRIMED_ASH = register(new Item(new Item.Settings().group(DesolationMod.DSL_GROUP)), "primed_ash");
    public static final Item CINDERFRUIT = register(new Item(new Item.Settings().group(DesolationMod.DSL_GROUP).food(new FoodComponent.Builder()
                    .hunger(5)
                    .saturationModifier(6.5F)
                    .alwaysEdible()
                    .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 200), 100)
                    .build())), "cinderfruit");
    public static final Item INFUSED_POWDER = register(new Item(new Item.Settings().group(DesolationMod.DSL_GROUP)), "infused_powder");
    public static final Item AIR_FILTER = register(new Item(new Item.Settings().group(DesolationMod.DSL_GROUP)), "air_filter");

    public static final ArmorMaterial maskMaterial = new MaskMaterial();
    public static final Item MASK = register(new ArmorItem(maskMaterial, EquipmentSlot.HEAD, new Item.Settings().group(DesolationMod.DSL_GROUP)), "mask");
    public static final Item GOGGLES = register(new ArmorItem(maskMaterial, EquipmentSlot.HEAD, new Item.Settings().group(DesolationMod.DSL_GROUP)), "goggles");



    public static final Item SPAWN_EGG_ASH_SCUTTLER = register(new SpawnEggItem(DesolationEntities.ASH_SCUTTLER,
            0x111111, 0xff7b00, new Item.Settings().group(ItemGroup.MISC)), "ash_scuttler_spawn_egg");
    public static final Item SPAWN_EGG_BLACKENED = register(new SpawnEggItem(DesolationEntities.BLACKENED,
            0x0a0a0a, 0xcf4b00, new Item.Settings().group(ItemGroup.MISC)), "blackened_spawn_egg");

    static void init() {
    }

    public static Item register(Item item, String path) {
        return Registry.register(Registry.ITEM, Desolation.id(path), item);
    }
}
