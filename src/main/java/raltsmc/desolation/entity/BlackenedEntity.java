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
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import raltsmc.desolation.entity.ai.goal.AshAttackGoal;
import raltsmc.desolation.registry.DesolationItems;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BlackenedEntity extends HostileEntity implements GeoEntity {
    private static final TrackedData<Boolean> MELEE_ATTACKING = DataTracker.registerData(BlackenedEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> ASH_ATTACKING = DataTracker.registerData(BlackenedEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("animation.desolation.blackened_idle");
    private static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("animation.desolation.blackened_hobble");
    private static final RawAnimation HEART_ANIM = RawAnimation.begin().thenLoop("animation.desolation.blackened_heartbeat");
    private static final RawAnimation MELEE_ANIM = RawAnimation.begin().thenLoop("animation.desolation.blackened_melee");
    private static final RawAnimation THROW_ANIM = RawAnimation.begin().thenLoop("animation.desolation.blackened_throw");

    public BlackenedEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(3, new FleeEntityGoal<>(this, WolfEntity.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.add(4, new AshAttackGoal(this, 1D, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createBlackenedAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.19D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(MELEE_ATTACKING, false);
        this.dataTracker.startTracking(ASH_ATTACKING, false);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_SKELETON_STEP, 0.5F, 1.0F);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    @Override
    public EntityGroup getGroup() { return EntityGroup.UNDEAD; }

    @Override
    protected void initEquipment(Random random, LocalDifficulty difficulty) {
        super.initEquipment(random, difficulty);
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(DesolationItems.ASH_PILE));
    }

    public void tryAshAttack(LivingEntity target) {
            World world = this.getWorld();
            Vec3d eyePos = this.getPos().add(new Vec3d(0, this.getEyeY() - this.getY(), 0).multiply(0.75));
            Vec3d targetVector = eyePos.add(target.getPos().subtract(this.getPos()).normalize().multiply(2.5));

            if (!world.isClient) {
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
        return this.dataTracker.get(MELEE_ATTACKING);
    }

    public boolean isAshAttacking() {
        return this.dataTracker.get(ASH_ATTACKING);
    }

    public void setMeleeAttacking(boolean val) {
        this.dataTracker.set(MELEE_ATTACKING, val);
    }

    public void setAshAttacking(boolean val) {
        this.dataTracker.set(ASH_ATTACKING, val);
    }

    private <E extends GeoAnimatable> PlayState idlePredicate(AnimationState<E> event) {
        if (!event.isMoving()) {
            event.getController().setAnimation(IDLE_ANIM);
            return PlayState.CONTINUE;
        } else {
            return PlayState.STOP;
        }
    }

    private <E extends GeoAnimatable> PlayState walkPredicate(AnimationState<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(WALK_ANIM);
            return PlayState.CONTINUE;
        } else {
            return PlayState.STOP;
        }
    }

    private <E extends GeoAnimatable> PlayState heartPredicate(AnimationState<E> event) {
        event.getController().setAnimation(HEART_ANIM);
        return PlayState.CONTINUE;
    }

    private <E extends GeoAnimatable> PlayState attackPredicate(AnimationState<E> event) {
        if (this.isMeleeAttacking()) {
            event.getController().setAnimation(MELEE_ANIM);
            return PlayState.CONTINUE;
        } else if (this.isAshAttacking()) {
            event.getController().setAnimation(THROW_ANIM);
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "idleController", 0, this::idlePredicate));
        controllerRegistrar.add(new AnimationController<>(this, "walkController", 0, this::walkPredicate));
        controllerRegistrar.add(new AnimationController<>(this, "heartController", 0, this::heartPredicate));
        controllerRegistrar.add(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}