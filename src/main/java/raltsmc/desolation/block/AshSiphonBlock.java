package raltsmc.desolation.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import raltsmc.desolation.block.entity.AshSiphonBlockEntity;

public class AshSiphonBlock extends Block implements BlockEntityProvider {

    public AshSiphonBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new AshSiphonBlockEntity();
    }
}
