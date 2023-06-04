package raltsmc.desolation.world.gen.sapling;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.function.Supplier;

public class CharredSaplingGenerator extends SaplingGenerator {
    private final Supplier<RegistryKey<ConfiguredFeature<?, ?>>> feature;

    public CharredSaplingGenerator(Supplier<RegistryKey<ConfiguredFeature<?, ?>>> feature) {
        this.feature = feature;
    }

    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return this.feature.get();
    }
}
