package raltsmc.desolation.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
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
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BlackenedEntity extends HostileEntity implements IAnimatable {
    private static final TrackedData<Boolean> MELEE_ATTACKING;
    private static final TrackedData<Boolean> ASH_ATTACKING;

    public BlackenedEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    private AnimationFactory factory = new AnimationFactory(this);

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    protected void initGoals() {
        this.goalSelector.add(3, new FleeEntityGoal(this, WolfEntity.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.add(4, new AshAttackGoal(this, 1D, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createBlackenedAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.19D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(MELEE_ATTACKING, false);
        this.dataTracker.startTracking(ASH_ATTACKING, false);
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

    public boolean isMeleeAttacking() {
        return (Boolean)this.dataTracker.get(MELEE_ATTACKING);
    }

    public boolean isAshAttacking() {
        return (Boolean)this.dataTracker.get(ASH_ATTACKING);
    }

    public void setMeleeAttacking(boolean val) {
        this.dataTracker.set(MELEE_ATTACKING, val);
    }

    public void setAshAttacking(boolean val) {
        this.dataTracker.set(ASH_ATTACKING, val);
    }

    private <E extends IAnimatable>PlayState idlePredicate(AnimationEvent<E> event) {
        if (!event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.desolation.blackened_idle", true));
            return PlayState.CONTINUE;
        } else {
            return PlayState.STOP;
        }
    }

    private <E extends IAnimatable>PlayState walkPredicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.desolation.blackened_hobble", true));
            return PlayState.CONTINUE;
        } else {
            return PlayState.STOP;
        }
    }

    private <E extends IAnimatable>PlayState heartPredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.desolation.blackened_heartbeat", true));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable>PlayState attackPredicate(AnimationEvent<E> event) {
        if (this.isMeleeAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.desolation.blackened_melee", true));
            return PlayState.CONTINUE;
        } else if (this.isAshAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.desolation.blackened_throw", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "idleController", 0, this::idlePredicate));
        data.addAnimationController(new AnimationController(this, "walkController", 0, this::walkPredicate));
        data.addAnimationController(new AnimationController(this, "heartController", 0, this::heartPredicate));
        data.addAnimationController(new AnimationController(this, "attackController", 0, this::attackPredicate));
    }

    static {
        MELEE_ATTACKING = DataTracker.registerData(BlackenedEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        ASH_ATTACKING = DataTracker.registerData(BlackenedEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
}