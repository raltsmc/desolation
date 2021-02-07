package raltsmc.desolation.mixin;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityPose;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import raltsmc.desolation.DesolationMod;
import raltsmc.desolation.entity.effect.DesolationStatusEffects;
import raltsmc.desolation.init.client.DesolationClient;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    public int cinderDashCooldownMax = 200;
    public int cinderDashCooldown = 200;
    public boolean isDashing = false;
    public int dashLengthMax = 10;
    public int dashLength = 0;
    public Vec3d dashVector;

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo info) {
        if (this.hasStatusEffect(DesolationStatusEffects.CINDER_SOUL)) {
            if (cinderDashCooldown < cinderDashCooldownMax) {
                ++cinderDashCooldown;
                if (cinderDashCooldown == cinderDashCooldownMax) {

                    PacketByteBuf buf = PacketByteBufs.create();
                    if (ClientPlayNetworking.canSend(DesolationMod.CINDER_SOUL_READY_PACKET_ID)) {
                        ClientPlayNetworking.send(DesolationMod.CINDER_SOUL_READY_PACKET_ID, buf);
                    }
                    this.playSound(SoundEvents.BLOCK_NOTE_BLOCK_CHIME, 1F, 1.2F);
                }
            }

            if (random.nextDouble() < 0.3) {
                PacketByteBuf buf = PacketByteBufs.create();
                if (ClientPlayNetworking.canSend(DesolationMod.CINDER_SOUL_TICK_PACKET_ID)) {
                    ClientPlayNetworking.send(DesolationMod.CINDER_SOUL_TICK_PACKET_ID, buf);
                }
            }

            if (DesolationClient.cinderDashBinding.isPressed() && cinderDashCooldown >= cinderDashCooldownMax) {
                dashVector = this.getRotationVector().normalize().multiply(0.75);
                cinderDashCooldown = 0;
                isDashing = true;
                PacketByteBuf buf = PacketByteBufs.create();
                if (ClientPlayNetworking.canSend(DesolationMod.DO_CINDER_DASH_PACKET_ID)) {
                    ClientPlayNetworking.send(DesolationMod.DO_CINDER_DASH_PACKET_ID, buf);
                }
            }

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
}

