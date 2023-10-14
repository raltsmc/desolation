package raltsmc.desolation.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CharredLogBlock extends PillarBlock {
    public CharredLogBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, pos, newState, moved);

        if (!state.isOf(newState.getBlock())) {
            CharredBranchBlock.notifyLossOfSupport(world, pos);
        }
    }
}
