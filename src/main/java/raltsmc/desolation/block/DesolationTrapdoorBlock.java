package raltsmc.desolation.block;

import net.minecraft.block.TrapdoorBlock;
import raltsmc.desolation.registry.DesolationBlockSets;

public class DesolationTrapdoorBlock extends TrapdoorBlock {
    public DesolationTrapdoorBlock(Settings settings) {
        super(settings, DesolationBlockSets.CHARRED);
    }
}
