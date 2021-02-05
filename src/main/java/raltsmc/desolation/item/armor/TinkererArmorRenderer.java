package raltsmc.desolation.item.armor;

import software.bernie.geckolib3.renderer.geo.GeoArmorRenderer;

public class TinkererArmorRenderer extends GeoArmorRenderer<TinkererArmorItem> {
    public TinkererArmorRenderer() {
        super(new TinkererArmorModel());

        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.rightLegBone = "armorRightLeg";
        this.leftLegBone = "armorLeftLeg";
        this.rightBootBone = "armorRightBoot";
        this.leftBootBone = "armorLeftBoot";
    }
}
