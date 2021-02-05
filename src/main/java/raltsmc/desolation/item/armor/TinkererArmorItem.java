package raltsmc.desolation.item.armor;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;

public class TinkererArmorItem extends GeoArmorItem implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);

    public TinkererArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Item.Settings builder) {
        super(materialIn, slot, builder);
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
