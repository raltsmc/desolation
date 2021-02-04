package raltsmc.desolation.item.blockitem;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class AshSiphonBlockItem extends BlockItem implements IAnimatable {
    private AnimationFactory factory = new AnimationFactory(this);

    public AshSiphonBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
