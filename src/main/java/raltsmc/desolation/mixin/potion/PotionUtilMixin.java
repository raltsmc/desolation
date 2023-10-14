package raltsmc.desolation.mixin.potion;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import raltsmc.desolation.registry.DesolationStatusEffects;

import java.util.List;

@Mixin(PotionUtil.class)
public class PotionUtilMixin {

    @Inject(method = "buildTooltip(Ljava/util/List;Ljava/util/List;F)V", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void desolation$addTooltip(List<StatusEffectInstance> statusEffects, List<Text> list, float f, CallbackInfo ci) {
        if (statusEffects.stream().anyMatch(e -> e.getEffectType().equals(DesolationStatusEffects.CINDER_SOUL))) {
            list.add(Text.translatable("potion.cinder_soul.tooltip_a").formatted(Formatting.GOLD));
            list.add(Text.translatable("potion.cinder_soul.tooltip_b").formatted(Formatting.GOLD));
        }
    }
}