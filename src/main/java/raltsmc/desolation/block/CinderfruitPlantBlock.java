package raltsmc.desolation.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.registry.DesolationItems;

import java.util.Random;

public class CinderfruitPlantBlock extends PlantBlock {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
    public static final IntProperty AGE;

    public CinderfruitPlantBlock(Settings settings) {
        super(settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Environment(EnvType.CLIENT)
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(DesolationItems.CINDERFRUIT);
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(DesolationBlocks.CHARRED_SOIL);
    }

    public boolean hasRandomTicks(BlockState state) {
        return (Integer)state.get(AGE) < 1;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = (Integer)state.get(AGE);
        if (i < 1 && random.nextInt(25) == 0) {
            world.setBlockState(pos, (BlockState)state.with(AGE, i + 1), 2);
        }
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int i = (Integer)state.get(AGE);
        boolean bl = i == 1;
        if (!bl) {
            //return ActionResult.PASS;
            return super.onUse(state, world, pos, player, hand, hit);
        } else {
            dropStack(world, pos, new ItemStack(DesolationItems.CINDERFRUIT, 1));
            world.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, SoundCategory.BLOCKS, 1.0F,
                    0.3F + world.random.nextFloat() * 0.4F);
            world.setBlockState(pos, (BlockState) state.with(AGE, 0), 2);
            return ActionResult.success(world.isClient);
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{AGE});
    }

    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) { return false; }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) { return true; }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int i = Math.min(3, (Integer)state.get(AGE) + 1);
        world.setBlockState(pos, (BlockState)state.with(AGE, i), 2);
    }

    static {
        AGE = Properties.AGE_1;
    }
}
