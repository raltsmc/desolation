package raltsmc.desolation.entity;

import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import raltsmc.desolation.registry.DesolationItems;

public class AshScuttlerEntity2 extends PathAwareEntity {
    public AshScuttlerEntity2(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new WanderAroundGoal(this, 0.2F));
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() == DesolationItems.CINDERFRUIT) {
            player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1.0F, 1.0F);
            itemStack.decrement(1);
            player.setStackInHand(hand, itemStack);
            return ActionResult.success(this.world.isClient);
        } else {
            return super.interactMob(player, hand);
        }
    }

    public EntityGroup getGroup() { return EntityGroup.ARTHROPOD; }
}