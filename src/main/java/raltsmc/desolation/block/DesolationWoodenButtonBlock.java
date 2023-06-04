package raltsmc.desolation.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.ButtonBlock;
import raltsmc.desolation.registry.DesolationBlockSets;

public class DesolationWoodenButtonBlock extends ButtonBlock {
    public DesolationWoodenButtonBlock(AbstractBlock.Settings settings) {
        super(settings, DesolationBlockSets.CHARRED, 30, true);
    }
}
