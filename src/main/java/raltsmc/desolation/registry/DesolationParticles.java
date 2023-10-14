package raltsmc.desolation.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import raltsmc.desolation.Desolation;

public class DesolationParticles {
    public static final DefaultParticleType SPARK = register("spark", FabricParticleTypes.simple());

    public static <T extends ParticleType<?>> T register(String name, T type) {
        return Registry.register(Registries.PARTICLE_TYPE, Desolation.id(name), type);
    }

    public static void init() { }
}
