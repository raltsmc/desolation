package raltsmc.desolation.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.registry.DesolationItems;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo info) {
        if (Objects.equals(this.world.getRegistryManager().get(Registry.BIOME_KEY).getId(this.world.getBiome(this.getBlockPos())), Desolation.id("charred_forest"))) {
            if (!this.world.isClient) {
                if (this.getEquippedStack(EquipmentSlot.HEAD).getItem() != DesolationItems.MASK
                && this.getEquippedStack(EquipmentSlot.HEAD).getItem() != DesolationItems.MASK_GOGGLES) {
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 300));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 300));
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
