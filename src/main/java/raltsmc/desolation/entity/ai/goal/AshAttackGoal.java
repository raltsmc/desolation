package raltsmc.desolation.entity.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Hand;
import raltsmc.desolation.entity.BlackenedEntity;

import java.util.EnumSet;

public class AshAttackGoal extends Goal {
    protected final BlackenedEntity mob;
    private final double speed;
    private final boolean pauseWhenMobIdle;
    private Path path;
    private double targetX;
    private double targetY;
    private double targetZ;
    private int updateCountdownTicks;
    private int attackCd;
    private static final long attackIntervalTicks = 20;
    private long lastUpdateTime;
    private AttackType lastAttack;

    private enum AttackType {
        MELEE,
        ASH,
        NONE
    }

    public AshAttackGoal(BlackenedEntity mob, double speed, boolean pauseWhenMobIdle) {
        this.mob = mob;
        this.speed = speed;
        this.pauseWhenMobIdle = pauseWhenMobIdle;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    public boolean canStart() {
        //if (Objects.equals(this.mob.getEquippedStack(EquipmentSlot.MAINHAND),new ItemStack(DesolationItems.ASH_PILE))) {
            long l = this.mob.getWorld().getTime();
            if (l - this.lastUpdateTime < attackIntervalTicks) {
                return false;
            } else {
                this.lastUpdateTime = l;
                LivingEntity livingEntity = this.mob.getTarget();
                if (livingEntity == null) {
                    return false;
                } else if (!livingEntity.isAlive()) {
                    return false;
                } else {
                    this.path = this.mob.getNavigation().findPathTo(livingEntity, 0);
                    if (this.path != null) {
                        return true;
                    } else {
                        return this.getSquaredMaxAttackDistance(livingEntity) >= this.mob.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                    }
                }
            }
        //} else {
        //   return false;
        //}
    }

    public boolean shouldContinue() {
        //if (Objects.equals(this.mob.getEquippedStack(EquipmentSlot.MAINHAND),new ItemStack(DesolationItems.ASH_PILE))) {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else if (!this.pauseWhenMobIdle) {
                return !this.mob.getNavigation().isIdle();
            } else if (!this.mob.isInWalkTargetRange(livingEntity.getBlockPos())) {
                return false;
            } else {
                return !(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity) livingEntity).isCreative();
            }
        //} else {
        //    return false;
        //}
    }

    public void start() {
        this.mob.getNavigation().startMovingAlong(this.path, this.speed);
        this.mob.setAttacking(true);
        this.updateCountdownTicks = 0;
        this.attackCd = 0;
        this.lastAttack = AttackType.NONE;
    }

    public void stop() {
        LivingEntity livingEntity = this.mob.getTarget();
        if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(livingEntity)) {
            this.mob.setTarget(null);
        }

        this.mob.setAttacking(false);
        this.mob.setMeleeAttacking(false);
        this.mob.setAshAttacking(false);
        this.mob.getNavigation().stop();
    }

    public void tick() {
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity == null) {
            return;
        }

        this.mob.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
        double d = this.mob.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
        this.updateCountdownTicks = Math.max(this.updateCountdownTicks - 1, 0);
        if ((this.pauseWhenMobIdle || this.mob.getVisibilityCache().canSee(livingEntity)) && this.updateCountdownTicks <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || livingEntity.squaredDistanceTo(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.mob.getRandom().nextFloat() < 0.05F)) {
            this.targetX = livingEntity.getX();
            this.targetY = livingEntity.getY();
            this.targetZ = livingEntity.getZ();
            this.updateCountdownTicks = 4 + this.mob.getRandom().nextInt(7);
            if (d > 1024.0D) {
                this.updateCountdownTicks += 10;
            } else if (d > 256.0D) {
                this.updateCountdownTicks += 5;
            }

            if (!this.mob.getNavigation().startMovingTo(livingEntity, this.speed)) {
                this.updateCountdownTicks += 15;
            }
        }

        this.attackCd = Math.max(this.attackCd - 1, 0);
        this.attack(livingEntity, d);
    }

    protected void attack(LivingEntity target, double squaredDistance) {
        double d = this.getSquaredMaxAttackDistance(target);
        double e = this.getSquaredCloseAttackDistance(target);
        if (squaredDistance <= e &&
                (this.lastAttack == AttackType.NONE && this.getAttackCd() <= 0) ||
                (this.lastAttack == AttackType.MELEE && this.getAttackCd() <= 0) ||
                (this.lastAttack == AttackType.ASH && this.getAttackCd() <= 30)) {
            this.resetAttackCd();
            this.mob.swingHand(Hand.MAIN_HAND);
            this.mob.setMeleeAttacking(true);
            this.mob.setAshAttacking(false);
            this.lastAttack = AttackType.MELEE;
            this.mob.tryAttack(target);
        } else if (squaredDistance <= d &&
                (this.lastAttack == AttackType.NONE && this.getAttackCd() <= 0) ||
                (this.lastAttack == AttackType.MELEE && this.getAttackCd() <= 30) ||
                (this.lastAttack == AttackType.ASH && this.getAttackCd() <= 0)) {
            this.resetAttackCd();
            this.mob.swingHand(Hand.MAIN_HAND);
            this.mob.setAshAttacking(true);
            this.mob.setMeleeAttacking(false);
            this.lastAttack = AttackType.ASH;
            this.mob.tryAshAttack(target);
        } else if (squaredDistance > d || this.getAttackCd() <= 0) {
            this.mob.setAshAttacking(false);
            this.mob.setMeleeAttacking(false);
            this.lastAttack = AttackType.NONE;
        }
    }

    protected void resetAttackCd() {
        this.attackCd = 60;
    }

    protected int getAttackCd() {
        return this.attackCd;
    }

    protected double getSquaredMaxAttackDistance(LivingEntity entity) {
        return this.mob.getWidth() * 4.0F * this.mob.getWidth() * 4.0F + entity.getWidth();
    }

    protected double getSquaredCloseAttackDistance(LivingEntity entity) {
        return this.mob.getWidth() * 2.0F * this.mob.getWidth() * 2.0F + entity.getWidth();
    }
}
