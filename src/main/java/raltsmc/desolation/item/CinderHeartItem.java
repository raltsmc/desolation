package raltsmc.desolation.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CinderHeartItem extends Item {
    public CinderHeartItem(Settings settings) {
        super(settings);
    }

    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
