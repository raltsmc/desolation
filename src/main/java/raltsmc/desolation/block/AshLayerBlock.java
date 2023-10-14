package raltsmc.desolation.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SnowBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;

public class AshLayerBlock extends SnowBlock {
    public AshLayerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if (state.get(LAYERS) > 1) {
            world.setBlockState(pos, this.getDefaultState().with(LAYERS, state.get(LAYERS) - 1), 1);
        }
    }
}
