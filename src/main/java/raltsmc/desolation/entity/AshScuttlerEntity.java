package raltsmc.desolation.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import raltsmc.desolation.entity.ai.goal.DigAshGoal;
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

public class AshScuttlerEntity extends PathAwareEntity implements GeoEntity {
    private static final TrackedData<Boolean> SEARCHING = DataTracker.registerData(AshScuttlerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final Ingredient ATTRACTING_INGREDIENT = Ingredient.ofItems(DesolationItems.CINDERFRUIT);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation HEAD_ANIM = RawAnimation.begin().thenLoop("animation.desolation.ash_scuttler_head");
    private static final RawAnimation WALK_ANIM = RawAnimation.begin().thenPlay("animation.desolation.ash_scuttler_walk");

    public AshScuttlerEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new DigAshGoal(this, 0.3D,40,2));
        this.goalSelector.add(2, new EscapeDangerGoal(this, 0.4F));
        this.goalSelector.add(3, new TemptGoal(this, 0.3D, ATTRACTING_INGREDIENT, false));
        this.goalSelector.add(4, new WanderAroundGoal(this, 0.2F));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8F));
        this.goalSelector.add(6, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SEARCHING, false);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_ENDERMITE_STEP, 0.5F, 1.0F);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_BAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BAT_DEATH;
    }

    public boolean isSearching() {
        return this.dataTracker.get(SEARCHING);
    }

    public void setSearching(boolean val) {
        this.dataTracker.set(SEARCHING, val);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (item == DesolationItems.CINDERFRUIT && !this.isSearching()) {
            if (!this.world.isClient) {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                this.dataTracker.set(SEARCHING, true);
                return ActionResult.SUCCESS;
            } else {
                double pVel = random.nextGaussian() * 0.02D;
                world.addParticle(ParticleTypes.HEART, this.getX(), this.getY(), this.getZ(), pVel, pVel, pVel);
                player.playSound(SoundEvents.ITEM_NETHER_WART_PLANT, 1.0F, 1.0F);
                return ActionResult.CONSUME;
            }
        }
        return super.interactMob(player, hand);
    }

    @Override
    public EntityGroup getGroup() { return EntityGroup.ARTHROPOD; }

    private <E extends GeoAnimatable> PlayState walkPredicate(AnimationState<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(WALK_ANIM);
            return PlayState.CONTINUE;
        } else {
            return PlayState.STOP;
        }
    }

    private <E extends GeoAnimatable> PlayState headPredicate(AnimationState<E> event) {
        if (!event.isMoving()) {
            event.getController().setAnimation(HEAD_ANIM);
            return PlayState.CONTINUE;
        } else {
            return PlayState.STOP;
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "walkController", 0, this::walkPredicate));
        controllerRegistrar.add(new AnimationController<>(this, "headController", 0, this::headPredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}