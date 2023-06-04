package raltsmc.desolation.client.render.entity.feature;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import raltsmc.desolation.Desolation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class GeoGlowLayerRenderer<T extends Entity & GeoAnimatable> extends GeoRenderLayer<T> {
    private final RenderLayer skin;

    public GeoGlowLayerRenderer(GeoRenderer<T> entityRendererIn, String texture) {
        super(entityRendererIn);
        this.skin = RenderLayer.getEyes(Desolation.id(texture));
    }

    @Override
    public void render(MatrixStack poseStack, T animatable, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(this.getEyesTexture());
        super.render(poseStack, animatable, bakedModel, this.getEyesTexture(), bufferSource, vertexConsumer, partialTick, 15728640, packedOverlay);
    }

    public RenderLayer getEyesTexture() {
        return skin;
    }
}
