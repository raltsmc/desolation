package raltsmc.desolation.world.gen.sapling;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.Supplier;

public class CharredSaplingGenerator extends SaplingGenerator {
    private final Supplier<ConfiguredFeature<TreeFeatureConfig, ?>> feature;

    public CharredSaplingGenerator(Supplier<ConfiguredFeature<TreeFeatureConfig, ?>> feature) {
        this.feature = feature;
    }

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
        return this.feature.get();
    }
}
