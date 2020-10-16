package raltsmc.desolation.entity;

import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import raltsmc.desolation.registry.DesolationItems;
import software.bernie.geckolib.animation.builder.AnimationBuilder;
import software.bernie.geckolib.animation.controller.AnimationController;
import software.bernie.geckolib.animation.controller.EntityAnimationController;
import software.bernie.geckolib.entity.IAnimatedEntity;
import software.bernie.geckolib.event.AnimationTestEvent;
import software.bernie.geckolib.manager.EntityAnimationManager;

public class AshScuttlerEntity extends PathAwareEntity implements IAnimatedEntity {
    private boolean isLooking = false;

    public AshScuttlerEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        registerAnimationControllers();
    }

    private EntityAnimationManager manager = new EntityAnimationManager();
    private AnimationController walkController = new EntityAnimationController(this, "walkController", 20,
            this::walkPredicate);
    private AnimationController lookController = new EntityAnimationController(this, "lookController", 20,
            this::headPredicate);

    protected void initGoals() {
        this.goalSelector.add(1, new EscapeDangerGoal(this, 0.4F));
        this.goalSelector.add(2, new WanderAroundGoal(this, 0.2F));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8F));
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

    @Override
    public EntityAnimationManager getAnimationManager() {
        return manager;
    }

    private <E extends AshScuttlerEntity> boolean walkPredicate(AnimationTestEvent<E> event) {
        if (event.isWalking()) {
            walkController.setAnimation(new AnimationBuilder().addAnimation("animation.desolation.ash_scuttler_walk"));
            return true;
        } else {
            return false;
        }
    }
    
    private <E extends AshScuttlerEntity> boolean headPredicate(AnimationTestEvent<E> event) {
        if (!event.isWalking()) {
            lookController.setAnimation(new AnimationBuilder().addAnimation("animation.desolation.ash_scuttler_head",
             true));
            return true;
        } else {
            return false;
        }
    }

    private void registerAnimationControllers() {
        manager.addAnimationController(walkController);
        manager.addAnimationController(lookController);
    }
}