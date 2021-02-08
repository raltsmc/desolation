package raltsmc.desolation.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import raltsmc.desolation.registry.DesolationEntities;
import raltsmc.desolation.registry.DesolationItems;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.EnumSet;
import java.util.UUID;

public class AshFlierEntity extends TameableEntity implements IAnimatable {

    private BlockPos circlingCenter;
    private boolean called;
    private boolean landing;
    private boolean seeking;
    private int tryTameCooldown;

    private AnimationFactory factory = new AnimationFactory(this);

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public AshFlierEntity(EntityType<? extends AshFlierEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 45, true);
        this.setTamed(false);
        this.setLanding(false);
        this.called = false;
        this.tryTameCooldown = 0;
    }

    protected void initGoals() {

        this.goalSelector.add(1, new SeekTameGoal(this, 50));
        this.goalSelector.add(2, new AshFlierEntity.LandGoal(this));
        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
        this.targetSelector.add(3, (new RevengeGoal(this)).setGroupRevenge());
        this.targetSelector.add(4, new FollowTargetGoal<BlackenedEntity>(this, BlackenedEntity.class, false));
        this.targetSelector.add(5, new UniversalAngerGoal(this, true));
    }

    public static DefaultAttributeContainer.Builder createAshFlierAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 12D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.8D);
    }

    protected SoundEvent getAmbientSound() { return SoundEvents.ITEM_ELYTRA_FLYING; }

    protected SoundEvent getHurtSound(DamageSource source) { return SoundEvents.ENTITY_BAT_HURT; }

    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_BAT_DEATH; }

    protected float getSoundVolume() { return 0.5f; }

    public EntityGroup getGroup() { return EntityGroup.DEFAULT; }

    public boolean canTarget(EntityType<?> type) { return true; }

    public AshFlierEntity createChild(ServerWorld world, PassiveEntity entity) {
        AshFlierEntity ashFlierEntity = (AshFlierEntity) DesolationEntities.ASH_FLIER.create(world);
        UUID uUID = this.getOwnerUuid();
        if (uUID != null) {
            ashFlierEntity.setOwnerUuid(uUID);
            ashFlierEntity.setTamed(true);
        }

        return ashFlierEntity;
    }

    @Override public boolean handleFallDamage(float fallDistance, float damageMultiplier) { return false; }

    @Override protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) { }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.isTouchingWater()) {
            this.updateVelocity(0.02F, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.800000011920929D));
        } else if (this.isInLava()) {
            this.updateVelocity(0.02F, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.5D));
        } else {
            float f = 0.91F;
            if (this.onGround) {
                f = this.world.getBlockState(new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ())).getBlock().getSlipperiness() * 0.91F;
            }

            float g = 0.16277137F / (f * f * f);
            f = 0.91F;
            if (this.onGround) {
                f = this.world.getBlockState(new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ())).getBlock().getSlipperiness() * 0.91F;
            }

            this.updateVelocity(this.onGround ? 0.1F * g : 0.02F, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply((double)f));
        }

        this.method_29242(this, false);
    }

    public void tick() {
        if (this.tryTameCooldown > 0) {
            this.tryTameCooldown--;
        }
        super.tick();
    }

    @Override public boolean isClimbing() { return false; }

    public boolean isLanding() {
        return this.landing;
    }

    public void setLanding(boolean landingIn) {
        this.landing = landingIn;
    }

    public boolean isSeeking() { return this.seeking; }

    public void setSeeking(boolean seekingIn) { this.seeking = true; }

    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        if (tag.contains("AX")) {
            this.circlingCenter = new BlockPos(tag.getInt("AX"), tag.getInt("AY"), tag.getInt("AZ"));
        }
        this.called = tag.getBoolean("Called");
    }

    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        /*tag.putInt("AX", this.circlingCenter.getX());
        tag.putInt("AY", this.circlingCenter.getY());
        tag.putInt("AZ", this.circlingCenter.getZ());*/
        tag.putBoolean("Called", this.called);
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world) {
            public boolean isValidPosition(BlockPos pos) {
                return !this.world.getBlockState(pos).isSolidBlock(world, pos);
            }
        };
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(false);
        birdNavigation.setCanEnterOpenDoors(false);
        return birdNavigation;
    }

    protected boolean hasWings() { return true; }

    public boolean tryTame(PlayerEntity player) {
        Hand[] var2 = Hand.values();
        int var3 = var2.length;

        for (Hand hand : var2) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (!this.isTamed() && itemStack.getItem() == DesolationItems.CINDERFRUIT && !this.world.isClient()) {
                if (!player.abilities.creativeMode) {
                    itemStack.decrement(1);
                }
                world.playSound((PlayerEntity)null, player.getBlockPos(), SoundEvents.ENTITY_PHANTOM_BITE, SoundCategory.NEUTRAL, 1f,1f);
                if (random.nextInt(5) == 0) {
                    this.setOwner(player);
                    this.navigation.stop();
                    this.setTarget((LivingEntity)null);
                    this.setLanding(true);
                    this.world.sendEntityStatus(this, (byte)7);
                    return true;
                } else {
                    this.world.sendEntityStatus(this, (byte)6);
                    return false;
                }
            }
        }
        return false;
    }

    class LandGoal extends Goal {
        private final AshFlierEntity flier;
        private final EntityNavigation navigation;
        private LivingEntity owner;

        private LandGoal(AshFlierEntity flier) {
            this.flier = flier;
            this.navigation = flier.getNavigation();
        }

        public boolean canStart() {
            LivingEntity livingEntity = flier.getOwner();
            if (livingEntity == null || livingEntity.isSpectator() || flier.called) {
                return false;
            } else {
                this.owner = livingEntity;
                return true;
            }
        }
    }

    class SeekTameGoal extends Goal {
        private final AshFlierEntity flier;
        //private final EntityNavigation navigation;
        private final World world;
        private final float seekDistance;
        private int timer;
        private PlayerEntity tamer;
        private final TargetPredicate tamerPredicate;
        private boolean hasTried;
        //private Path pathToTamer;

        private SeekTameGoal(AshFlierEntity flier, float seekDistance) {
            this.flier = flier;
            this.world = flier.world;
            //this.navigation = flier.getNavigation();
            this.seekDistance = seekDistance;
            this.tamerPredicate = (new TargetPredicate()).setBaseMaxDistance((double)seekDistance).includeInvulnerable().includeTeammates().ignoreEntityTargetRules();
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        public boolean canStart() {
            this.tamer = this.world.getClosestPlayer(this.tamerPredicate, this.flier);
            return this.tamer != null && this.isAttractive(this.tamer) && this.flier.tryTameCooldown <= 0;
        }

        public boolean shouldContinue() {
            if (this.tamer == null ) {
                return false;
            } else if (!this.tamer.isAlive()) {
                return false;
            } else if (this.flier.squaredDistanceTo(this.tamer) > (double)(this.seekDistance * this.seekDistance)) {
                return false;
            } else {
                return this.isAttractive(this.tamer);
            }
        }

        public void start() {
            this.flier.setSeeking(true);
            this.timer = 0;
            this.hasTried = false;
            //this.pathToTamer = this.navigation.findPathTo(this.tamer, (int)this.seekDistance*3);
            this.flier.navigation.setRangeMultiplier(5);
            this.flier.navigation.startMovingTo(this.tamer, 5);
        }

        public void stop() {
            this.flier.setSeeking(false);
            this.flier.navigation.stop();
            this.timer = 0;
            this.tamer = null;
            super.stop();
        }

        public void tick() {
            //this.navigation.recalculatePath();
            //this.flier.navigation.tick();
            if (timer % 40 == 0 && this.flier.distanceTo(tamer) > 2) {
                this.flier.navigation.startMovingTo(this.tamer, 5);
            } else if (this.flier.distanceTo(tamer) < 2) {
                this.flier.tryTame(tamer);
                this.flier.tryTameCooldown = 200;
                this.stop();
            } else {
                timer++;
            }
            //System.out.println("Tamer position: " + this.tamer.getPos() + "/ Navigation: " + this.flier.navigation.isFollowingPath());
        }



        private boolean isAttractive(PlayerEntity player) {
            Hand[] var2 = Hand.values();
            int var3 = var2.length;

            for (Hand hand : var2) {
                ItemStack itemStack = player.getStackInHand(hand);
                if (!this.flier.isTamed() && itemStack.getItem() == DesolationItems.CINDERFRUIT) {
                    return true;
                }
            }

            return false;
        }
    }

    private <E extends IAnimatable>PlayState floatPredicate(AnimationEvent<E> event) {
        if (!this.isLanding()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ash_flier.float"));
            return PlayState.CONTINUE;
        } else {
            return PlayState.STOP;
        }
    }

    private <E extends IAnimatable>PlayState landPredicate(AnimationEvent<E> event) {
        if (this.isLanding()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ash_flier.land"));
            return PlayState.CONTINUE;
        } else {
            return PlayState.STOP;
        }
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "floatController", 40, this::floatPredicate));
        data.addAnimationController(new AnimationController(this, "landController", 40, this::landPredicate));
    }
}
