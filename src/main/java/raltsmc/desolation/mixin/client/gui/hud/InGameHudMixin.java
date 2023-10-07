package raltsmc.desolation.mixin.client.gui.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.registry.DesolationItems;

import java.util.Optional;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Unique
    private static final Identifier GOGGLES_OVERLAY = Desolation.id("textures/misc/goggles_overlay.png");

    @Shadow
    @Final
    private MinecraftClient client;
    @Shadow
    private int scaledWidth;
    @Shadow
    private int scaledHeight;

    @Shadow
    protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Inject(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/client/network/ClientPlayerEntity.getFrozenTicks()I", ordinal = 0))
    private void desolation$renderGoggles(DrawContext context, float tickDelta, CallbackInfo ci) {
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(this.client.player);
        if (this.client.options.getPerspective().isFirstPerson()
                && Desolation.CONFIG.showGogglesOverlay
                && component.isPresent()
                && component.get().isEquipped(DesolationItems.GOGGLES)) {
            this.desolation$renderGogglesTranslucency();
            this.renderOverlay(context, GOGGLES_OVERLAY, 1.0F);
        }
    }

    private void desolation$renderGogglesTranslucency() {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 0.36F);
        //RenderSystem.disableTexture();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(0.0D, this.scaledHeight, -90.0D).texture(0.0F, 1.0F).next();
        bufferBuilder.vertex(this.scaledWidth, this.scaledHeight, -90.0D).texture(1.0F, 1.0F).next();
        bufferBuilder.vertex(this.scaledWidth, 0.0D, -90.0D).texture(1.0F, 0.0F).next();
        bufferBuilder.vertex(0.0D, 0.0D, -90.0D).texture(0.0F, 0.0F).next();
        tessellator.draw();
        RenderSystem.depthMask(true);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        //RenderSystem.enableTexture();
    }
}