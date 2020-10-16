package raltsmc.desolation.block;

import javafx.beans.property.Property;
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class AshLayerBlock extends SnowBlock {
    public AshLayerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if ( state.get(LAYERS) > 1 ) {
            world.setBlockState(pos, this.getDefaultState().with(LAYERS, state.get(LAYERS) - 1), 1);
        }
    }
}
