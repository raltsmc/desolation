package raltsmc.desolation.mixin;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Arm;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.access.PlayerEntityAccess;
import raltsmc.desolation.config.DesolationConfig;
import raltsmc.desolation.entity.effect.DesolationStatusEffects;
import raltsmc.desolation.init.client.DesolationClient;
import raltsmc.desolation.registry.DesolationItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin extends LivingEntity implements PlayerEntityAccess {
    public int cinderDashCooldownMax = 200;
    public int cinderDashCooldown = 200;
    public boolean isDashing = false;
    public int dashLengthMax = 10;
    public int dashLength = 0;
    public Vec3d dashVector;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo info) {
        // ugly but works =)
        // TODO make not ugly but still work (biome tags????)
        if ((Objects.equals(this.world.getRegistryManager().get(Registry.BIOME_KEY).getId(this.world.getBiome(this.getBlockPos())), Desolation.id("charred_forest"))
                || Objects.equals(this.world.getRegistryManager().get(Registry.BIOME_KEY).getId(this.world.getBiome(this.getBlockPos())), Desolation.id("charred_forest_small"))
                || Objects.equals(this.world.getRegistryManager().get(Registry.BIOME_KEY).getId(this.world.getBiome(this.getBlockPos())), Desolation.id("charred_forest_clearing")))
                && this.getY() >= world.getSeaLevel() - 10) {
            if (!this.world.isClient) {
                if (this.getEquippedStack(EquipmentSlot.HEAD).getItem() != DesolationItems.MASK
                && this.getEquippedStack(EquipmentSlot.HEAD).getItem() != DesolationItems.MASK_GOGGLES) {
                //&& this.config.inflictBiomeDebuffs) {
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 308));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 308));
                }
            }
        }
        // TODO make goggles only stop blindness from ash rather than all blindness
        if (this.hasStatusEffect(StatusEffects.BLINDNESS)
                && (this.getEquippedStack(EquipmentSlot.HEAD).getItem() == DesolationItems.GOGGLES
                || this.getEquippedStack(EquipmentSlot.HEAD).getItem() == DesolationItems.MASK_GOGGLES)) {
            this.removeStatusEffect(StatusEffects.BLINDNESS);
        }
        
        if (this.hasStatusEffect(DesolationStatusEffects.CINDER_SOUL)) {
            // praise jesus if any of this works in multiplayer
            if (cinderDashCooldown < cinderDashCooldownMax) {
                ++cinderDashCooldown;
                if (cinderDashCooldown == cinderDashCooldownMax) {
                    if (this.world.isClient) {
                        this.playSound(SoundEvents.BLOCK_NOTE_BLOCK_CHIME, 1F, 1.2F);
                    } else {
                        List<Vec3d> points = new ArrayList<Vec3d>();

                        double phi = Math.PI * (3. - Math.sqrt(5.));
                        for (int i = 0; i <= 250; ++i) {
                            double y = 1 - (i / (float) (250 - 1)) * 2;
                            double radius = Math.sqrt(1 - y * y);
                            double theta = phi * i;
                            double x = Math.cos(theta) * radius;
                            double z = Math.sin(theta) * radius;

                            points.add(new Vec3d(this.getX() + x * 0.5, this.getY() + 1 + y, this.getZ() + z * 0.5));
                        }

                        for (Vec3d vec : points) {
                            Vec3d vel = vec.subtract(this.getPos())
                                    .normalize()
                                    .multiply(0.12 + random.nextDouble() * 0.03)
                                    .add(this.getVelocity().multiply(1, 0.1, 1))
                                    .multiply(1.25, 1, 1.25);
                            this.world.addParticle(ParticleTypes.FLAME, vec.x, vec.y, vec.z, vel.x, vel.y, vel.z);
                        }
                    }
                }
            }
            if (!world.isClient && random.nextDouble() < 0.3) {
                double d = this.getX() - 0.25D + random.nextDouble() / 2;
                double e = this.getY();
                double f = this.getZ() - 0.25D + random.nextDouble() / 2;

                double g = random.nextDouble() * 0.6D - 0.3D;
                double h = random.nextDouble() * 6.0D / 16.0D;
                double i = (random.nextDouble() - 0.5D) / 5.0D;

                this.world.addParticle(ParticleTypes.FLAME, d + g, e + h, f + g, 0.0D, 0.1D + i, 0.0D);
                if (random.nextDouble() < 0.25) {
                    this.world.playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.AMBIENT, .8F, 1F, false);
                }
            }
            /*if (world.isClient) {
                if (DesolationClient.cinderDashBinding.isPressed() && cinderDashCooldown >= cinderDashCooldownMax) {
                    dashVector = this.getRotationVector().normalize().multiply(0.75);
                    cinderDashCooldown = 0;
                    isDashing = true;
                    //this.world.playSound(this.getX(), this.getY(), this.getZ(),SoundEvents.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.PLAYERS, 1F, 1.6F, false);
                }
            }*/
            if (isDashing) {
                if (dashLength < dashLengthMax) {
                    if (this.world.isClient) {
                        this.setVelocity(dashVector);
                        this.velocityDirty = true;
                    }
                    this.setPose(EntityPose.SWIMMING);
                    this.fallDistance = 0;
                    ++dashLength;
                } else {
                    isDashing = false;
                    dashLength = 0;
                }
            }
        } else {
            dashLength = 0;
            isDashing = false;
        }
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getHealth()F", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    private void doFireAttackA(Entity target, CallbackInfo info, float f, float h, boolean bl, boolean bl2, int j, boolean bl3, boolean bl4, float k, boolean bl5, int l) {
        if (l <= 0 && !target.isOnFire() && this.hasStatusEffect(DesolationStatusEffects.CINDER_SOUL)) {
            bl5 = true;
            target.setOnFireFor(1);
        }
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;increaseStat(Lnet/minecraft/util/Identifier;I)V", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    private void doFireAttackB(Entity target, CallbackInfo info, float f, float h, boolean bl, boolean bl2, int j, boolean bl3, boolean bl4, float k, boolean bl5, int l, float n) {
        if (l <= 0 && this.hasStatusEffect(DesolationStatusEffects.CINDER_SOUL)) {
            target.setOnFireFor(6);
        }
    }

    @Override
    public void doDash() {
        if (cinderDashCooldown >= cinderDashCooldownMax) {
            dashVector = this.getRotationVector().normalize().multiply(0.75);
            cinderDashCooldown = 0;
            isDashing = true;
            //this.playSound(SoundEvents.ENTITY_ENDER_DRAGON_GROWL, 1F, 1.6F);
        }
    }

    @Shadow
    @Override
    public Iterable<ItemStack> getArmorItems() {
        return null;
    }

    @Shadow
    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return null;
    }

    @Shadow
    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

    @Shadow
    @Override
    public Arm getMainArm() {
        return null;
    }
}
