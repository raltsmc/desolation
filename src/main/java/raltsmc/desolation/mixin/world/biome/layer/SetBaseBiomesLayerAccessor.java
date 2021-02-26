package raltsmc.desolation.mixin.world.biome.layer;

import net.minecraft.world.biome.layer.SetBaseBiomesLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SetBaseBiomesLayer.class)
public interface SetBaseBiomesLayerAccessor {
    @Accessor("TEMPERATE_BIOMES")
    public static int[] getTemperateBiomes() {
        throw new AssertionError();
    }

    @Accessor("TEMPERATE_BIOMES")
    public static void setTemperateBiomes(int[] biomes) {
        throw new AssertionError();
    }
}
