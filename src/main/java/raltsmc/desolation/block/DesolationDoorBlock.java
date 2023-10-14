package raltsmc.desolation.block;

import net.minecraft.block.DoorBlock;
import raltsmc.desolation.registry.DesolationBlockSets;

public class DesolationDoorBlock extends DoorBlock {
    public DesolationDoorBlock(Settings settings) {
        super(settings, DesolationBlockSets.CHARRED);
    }
}
