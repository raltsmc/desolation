package raltsmc.desolation.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
import raltsmc.desolation.entity.effect.DesolationStatusEffects;
import raltsmc.desolation.registry.DesolationItems;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo info) {
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
