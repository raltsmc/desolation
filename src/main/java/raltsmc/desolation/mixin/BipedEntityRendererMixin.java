package raltsmc.desolation.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import raltsmc.desolation.client.HeadGearFeatureRenderer;

@Mixin(BipedEntityRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class BipedEntityRendererMixin<T extends MobEntity, M extends BipedEntityModel<T>> extends MobEntityRenderer<T, M> {
    public BipedEntityRendererMixin(EntityRenderDispatcher dispatcher, M entityModel, float f) {
        super(dispatcher, entityModel, f);
    }

    @Inject(at=@At("TAIL"), method = "<init>(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;Lnet/minecraft/client/render/entity/model/BipedEntityModel;FFFF)V")
    public void addCustomRenderers(CallbackInfo ci) {
        this.addFeature(new HeadGearFeatureRenderer(((BipedEntityRenderer)(Object)this), new BipedEntityModel(1.4f)));
    }
}
