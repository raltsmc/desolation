package raltsmc.desolation.mixin.entity.player;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.registry.DesolationStatusEffects;
import raltsmc.desolation.registry.DesolationItems;

import java.util.Optional;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow
    @Override
    public abstract Iterable<ItemStack> getArmorItems();

    @Shadow
    @Override
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow
    @Override
    public abstract void equipStack(EquipmentSlot slot, ItemStack stack);

    @Shadow
    @Override
    public abstract Arm getMainArm();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void desolation$tickPlayerEntity(CallbackInfo info) {
        World world = this.getWorld();

        if (!world.isClient) {
            Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(this);
            Identifier biomeId = world.getRegistryManager().get(RegistryKeys.BIOME).getId(world.getBiome(this.getBlockPos()).value());

            if (this.getY() >= world.getSeaLevel() - 10 && biomeId != null && Desolation.MOD_ID.equals(biomeId.getNamespace())) {
                if (component.isEmpty() || !component.get().isEquipped(DesolationItems.MASK)) {
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 308));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 308));
                }
            }

            if (this.hasStatusEffect(StatusEffects.BLINDNESS) && component.isPresent() && component.get().isEquipped(DesolationItems.GOGGLES)) {
                this.removeStatusEffect(StatusEffects.BLINDNESS);
            }
        }
    }

    /*
     * This causes logic in attack() to treat attackers with Cinder Soul as if they had Fire Aspect.
     * The purpose is to cause killed entities to be treated as if they were on fire when they died.
     */
    @WrapOperation(method = "attack", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/enchantment/EnchantmentHelper;getFireAspect(Lnet/minecraft/entity/LivingEntity;)I"
    ))
    @SuppressWarnings("unused")
    private int desolation$igniteTarget(LivingEntity entity, Operation<Integer> operation) {
        int level = operation.call(entity);

        if (level < 1 && entity.hasStatusEffect(DesolationStatusEffects.CINDER_SOUL)) {
            level = 1;
        }

        return level;
    }

    /*
     * In the event an attacker with Cinder Soul is setting a target on fire, this increases
     * the duration of the status ailment if Cinder Soul would outlast Fire Aspect.
     */
    @WrapOperation(method = "attack", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/Entity;setOnFireFor(I)V",
            ordinal = 1
    ))
    @SuppressWarnings("unused")
    private void desolation$burnTarget(Entity instance, int duration, Operation<Integer> operation) {
        if (duration < 6 && this.hasStatusEffect(DesolationStatusEffects.CINDER_SOUL)) {
            duration = 6;
        }

        operation.call(instance, duration);
    }
}
