package raltsmc.desolation.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import raltsmc.desolation.entity.ai.goal.AshAttackGoal;
import raltsmc.desolation.registry.DesolationItems;
import software.bernie.geckolib.animation.builder.AnimationBuilder;
import software.bernie.geckolib.animation.controller.AnimationController;
import software.bernie.geckolib.animation.controller.EntityAnimationController;
import software.bernie.geckolib.entity.IAnimatedEntity;
import software.bernie.geckolib.event.AnimationTestEvent;
import software.bernie.geckolib.manager.EntityAnimationManager;

public class BlackenedEntity extends HostileEntity implements IAnimatedEntity {
    public BlackenedEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        registerAnimationControllers();
    }

    private EntityAnimationManager manager = new EntityAnimationManager();
    private AnimationController idleController = new EntityAnimationController(this, "idleController", 20, this::idlePredicate);
    private AnimationController walkController = new EntityAnimationController(this, "walkController", 20, this::walkPredicate);
    private AnimationController heartController = new EntityAnimationController(this, "heartController", 20, this::heartPredicate);

    protected void initGoals() {
        this.goalSelector.add(3, new FleeEntityGoal(this, WolfEntity.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.add(4, new AshAttackGoal(this, 1D, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(2, new FollowTargetGoal(this, PlayerEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createBlackenedAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.19D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6);
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_SKELETON_STEP, 0.5F, 1.0F);
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    public EntityGroup getGroup() { return EntityGroup.UNDEAD; }

    protected void initEquipment(LocalDifficulty difficulty) {
        super.initEquipment(difficulty);
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(DesolationItems.ASH_PILE));
    }

    public void tryAshAttack(LivingEntity target) {
            Vec3d eyePos = this.getPos().add(new Vec3d(0, this.getEyeY() - this.getY(), 0).multiply(0.75));
            Vec3d targetVector = eyePos.add(target.getPos().subtract(this.getPos()).normalize().multiply(2.5));

            if (!this.world.isClient) {
                AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(world, targetVector.x,
                        targetVector.y, targetVector.z);
                areaEffectCloudEntity.setDuration(30);
                areaEffectCloudEntity.setParticleType(ParticleTypes.WHITE_ASH);
                areaEffectCloudEntity.setColor(0xcccccc);
                areaEffectCloudEntity.addEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 120, 2));
                areaEffectCloudEntity.setRadius(0.6F);
                areaEffectCloudEntity.setRadiusOnUse(0.6F);
                areaEffectCloudEntity.setRadiusGrowth(0.03F);
                areaEffectCloudEntity.setOwner(this);
                areaEffectCloudEntity.setWaitTime(0);
                areaEffectCloudEntity.playSound(SoundEvents.BLOCK_SNOW_BREAK, 1, 1);
                world.spawnEntity(areaEffectCloudEntity);
            }
    }

    @Override
    public EntityAnimationManager getAnimationManager() {
        return manager;
    }

    private <E extends BlackenedEntity> boolean idlePredicate(AnimationTestEvent<E> event) {
        if (!event.isWalking()) {
            idleController.setAnimation(new AnimationBuilder().addAnimation("animation.desolation.blackened_idle", true));
            return true;
        } else {
            return false;
        }
    }

    private <E extends BlackenedEntity> boolean walkPredicate(AnimationTestEvent<E> event) {
        if (event.isWalking()) {
            walkController.setAnimation(new AnimationBuilder().addAnimation("animation.desolation.blackened_hobble", true));
            return true;
        } else {
            return false;
        }
    }

    private <E extends BlackenedEntity> boolean heartPredicate(AnimationTestEvent<E> event) {
        heartController.setAnimation(new AnimationBuilder().addAnimation("animation.desolation.blackened_heartbeat", true));
        return true;
    }

    private void registerAnimationControllers() {
        manager.addAnimationController(idleController);
        manager.addAnimationController(walkController);
        manager.addAnimationController(heartController);
    }
}