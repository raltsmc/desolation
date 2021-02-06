package raltsmc.desolation.client.renderer;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import raltsmc.desolation.block.entity.AshSiphonBlockEntity;
import raltsmc.desolation.client.model.AshSiphonBlockEntityModel;
import software.bernie.geckolib3.renderer.geo.GeoBlockRenderer;

public class AshSiphonBlockEntityRenderer extends GeoBlockRenderer<AshSiphonBlockEntity> {
    public AshSiphonBlockEntityRenderer(BlockEntityRenderDispatcher renderDispatcher) {
        super(renderDispatcher, new AshSiphonBlockEntityModel());
    }

    // TODO fix block rendering as solid w/ Canvas
    @Override
    public RenderLayer getRenderType(AshSiphonBlockEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntitySolid(getTextureLocation(animatable));
    }
}
