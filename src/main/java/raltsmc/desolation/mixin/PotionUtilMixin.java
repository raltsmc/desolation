package raltsmc.desolation.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import raltsmc.desolation.entity.effect.DesolationStatusEffects;

import java.util.List;

@Mixin(PotionUtil.class)
public class PotionUtilMixin {

    @Inject(method = "buildTooltip", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void buildTooltip(ItemStack stack, List<Text> list, float f, CallbackInfo ci, List<StatusEffectInstance> list2) {
        if (list2.stream().anyMatch(e -> e.getEffectType().equals(DesolationStatusEffects.CINDER_SOUL))) {
            list.add((new TranslatableText("potion.cinder_soul.tooltip_a")).formatted(Formatting.GOLD));
            list.add((new TranslatableText("potion.cinder_soul.tooltip_b")).formatted(Formatting.GOLD));
        }
    }
}