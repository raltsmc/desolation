package raltsmc.desolation.mixin;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.VanillaLayeredBiomeSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(VanillaLayeredBiomeSource.class)
public interface VanillaLayeredBiomeSourceAccessor {
    @Accessor("BIOMES")
    public static List<RegistryKey<Biome>> getBiomes() {
        throw new AssertionError();
    }

    @Accessor("BIOMES")
    public static void setBiomes(List<RegistryKey<Biome>> biomes) {
        throw new AssertionError();
    }
}
