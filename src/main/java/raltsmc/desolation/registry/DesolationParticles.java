package raltsmc.desolation.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.impl.client.particle.ParticleFactoryRegistryImpl;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.client.particle.SparkParticle;

public class DesolationParticles {
    public static final DefaultParticleType SPARK;

    public static void init() {
        register(SPARK, "spark");
    }

    static {
        SPARK = FabricParticleTypes.simple();
    }

    public static void register(DefaultParticleType type, String id) {
        Registry.register(Registry.PARTICLE_TYPE, Desolation.id(id), type);
    }
}
