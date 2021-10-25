package raltsmc.desolation.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import raltsmc.desolation.registry.DesolationBlocks;

public class CooledEmberBlock extends Block {
    public CooledEmberBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem() == Items.FLINT_AND_STEEL) {
            if (!world.isClient) {
                world.setBlockState(pos, DesolationBlocks.EMBER_BLOCK.getDefaultState());
                world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1f, 1f);
                if (!player.getAbilities().creativeMode) {
                    stack.damage(1, (LivingEntity)player, (p) -> {
                        p.sendToolBreakStatus(hand);
                    });
                }
            }
            return ActionResult.success(world.isClient);
        } else if (stack.getItem() == Items.FIRE_CHARGE) {
            if (!world.isClient) {
                world.setBlockState(pos, DesolationBlocks.EMBER_BLOCK.getDefaultState());
                world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1f, 1f);
                if (!player.getAbilities().creativeMode) {
                    stack.decrement(1);
                }
            }
            return ActionResult.success(world.isClient);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
