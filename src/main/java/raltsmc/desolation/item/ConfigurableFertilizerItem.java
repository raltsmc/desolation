package raltsmc.desolation.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ConfigurableFertilizerItem extends Item {
    private double growChance;
    private int growTries;

    public ConfigurableFertilizerItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        if (useOnFertilizable(context.getStack(), world, blockPos, growChance, growTries)) {
            if (!world.isClient) {
                world.syncWorldEvent(2005,blockPos,0);
            }
            return ActionResult.success(world.isClient);
        } else {
            return ActionResult.PASS;
        }
    }

    public static boolean useOnFertilizable(ItemStack stack, World world, BlockPos pos, double growChance, int growTries) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.getBlock() instanceof Fertilizable) {
            Fertilizable fertilizable = (Fertilizable)blockState.getBlock();
            if (fertilizable.isFertilizable(world, pos, blockState, world.isClient)) {
                if (world instanceof ServerWorld) {
                    for (int i=0; i<growTries; i++) {
                        if (fertilizable.canGrow(world, world.random, pos, blockState) && RANDOM.nextDouble() < growChance) {
                            fertilizable.grow((ServerWorld)world, world.random, pos, blockState);
                        }
                    }
                    stack.decrement(1);
                }
                return true;
            }
        }
        return false;
    }

    public void setGrowChance(double growChance) {
        this.growChance = growChance;
    }

    public double getGrowChance() {
        return this.growChance;
    }

    public void setGrowTries(int growTries) {
        this.growTries = growTries;
    }

    public int getGrowTries() {
        return this.growTries;
    }
}
