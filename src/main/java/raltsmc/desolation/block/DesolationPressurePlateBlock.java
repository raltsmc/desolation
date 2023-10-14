package raltsmc.desolation.block;

import net.minecraft.block.PressurePlateBlock;
import raltsmc.desolation.registry.DesolationBlockSets;

public class DesolationPressurePlateBlock extends PressurePlateBlock {
    public DesolationPressurePlateBlock(ActivationRule activationRule, Settings settings) {
        super(activationRule, settings, DesolationBlockSets.CHARRED);
    }
}
