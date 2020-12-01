package raltsmc.desolation.mixin;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.BiomeEffectSoundPlayer;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.config.DesolationConfig;

import java.util.Objects;

@Environment(EnvType.CLIENT)
@Mixin(BiomeEffectSoundPlayer.class)
public class BiomeEffectSoundPlayerMixin {
    DesolationConfig config = AutoConfig.getConfigHolder(DesolationConfig.class).getConfig();

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
    private void stopSound(SoundEvent soundEvent, Biome biome, BiomeEffectSoundPlayer.MusicLoop musicLoop, CallbackInfoReturnable<BiomeEffectSoundPlayer.MusicLoop> info) {
        if ((Objects.equals(player.world.getRegistryManager().get(Registry.BIOME_KEY).getId(biome), Desolation.id("charred_forest"))
                || Objects.equals(player.world.getRegistryManager().get(Registry.BIOME_KEY).getId(biome), Desolation.id("charred_forest_small"))
                || Objects.equals(player.world.getRegistryManager().get(Registry.BIOME_KEY).getId(biome), Desolation.id("charred_forest_clearing")))
                && !config.biomeSoundAmbience
        ) {
            this.soundManager.stop(musicLoop);
            info.setReturnValue(musicLoop);
        }
    }

}
