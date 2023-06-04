package raltsmc.desolation.mixin.client.sound;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.BiomeEffectSoundPlayer;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import raltsmc.desolation.Desolation;

import java.util.Objects;

@Environment(EnvType.CLIENT)
@Mixin(BiomeEffectSoundPlayer.class)
public class BiomeEffectSoundPlayerMixin {
    @Shadow @Final private ClientPlayerEntity player;
    @Shadow @Final private SoundManager soundManager;

    @Inject(method = "method_25459",
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/sound/BiomeEffectSoundPlayer$MusicLoop;fadeIn()V",
                    ordinal = 0),
            cancellable = true
    )
    private void stopSound(RegistryEntry<Biome> registryEntry, Biome sound, BiomeEffectSoundPlayer.MusicLoop loop, CallbackInfoReturnable<BiomeEffectSoundPlayer.MusicLoop> cir) {
        if ((Objects.equals(player.world.getRegistryManager().get(RegistryKeys.BIOME).getId(sound), Desolation.id("charred_forest"))
                || Objects.equals(player.world.getRegistryManager().get(RegistryKeys.BIOME).getId(sound), Desolation.id("charred_forest_small"))
                || Objects.equals(player.world.getRegistryManager().get(RegistryKeys.BIOME).getId(sound), Desolation.id("charred_forest_clearing")))
                && !Desolation.CONFIG.biomeSoundAmbience
        ) {
            this.soundManager.stop(loop);
            cir.setReturnValue(loop);
        }
    }

}
