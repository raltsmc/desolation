package raltsmc.desolation.client.renderer;

import raltsmc.desolation.client.model.AshSiphonBlockEntityModel;
import software.bernie.geckolib3.renderer.geo.GeoItemRenderer;

public class AshSiphonBlockItemRenderer extends GeoItemRenderer {
    public AshSiphonBlockItemRenderer() {
        super(new AshSiphonBlockEntityModel());
    }

}
