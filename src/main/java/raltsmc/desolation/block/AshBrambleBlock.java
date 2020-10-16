package raltsmc.desolation.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AshBrambleBlock extends Block {
    public AshBrambleBlock(Settings settings) { super(settings); }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        entity.slowMovement(state, new Vec3d(0.85D, 1D, 0.85D));
    }
}
