package raltsmc.desolation.client.render.entity.feature;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import raltsmc.desolation.Desolation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class GeoGlowLayerRenderer<T extends Entity & IAnimatable> extends GeoLayerRenderer<T> {
    private final RenderLayer skin;
    private final IGeoRenderer<T> renderer;

    public GeoGlowLayerRenderer(IGeoRenderer<T> entityRendererIn, String texture) {
        super(entityRendererIn);
        this.skin = RenderLayer.getEyes(Desolation.id(texture));
        this.renderer = entityRendererIn;
    }

    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexConsumer = bufferIn.getBuffer(this.getEyesTexture());
        GeoModelProvider<T> modelProvider = (GeoModelProvider<T>) getEntityModel();
        GeoModel model = modelProvider.getModel(modelProvider.getModelLocation(entitylivingbaseIn));
        renderer.render(model, entitylivingbaseIn,
                partialTicks,
                this.getEyesTexture(),
                matrixStackIn,
                bufferIn, vertexConsumer, 15728640, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public RenderLayer getEyesTexture() {
        return skin;
    }
}
