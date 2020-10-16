package raltsmc.desolation.registry;

import com.google.common.collect.Sets;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import raltsmc.desolation.Desolation;

import static net.minecraft.block.SnowBlock.LAYERS;
import static raltsmc.desolation.block.CinderfruitPlantBlock.AGE;

public final class DesolationStructures {

    public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_CHARRED = (
            register(
                    Feature.TREE.configure((new TreeFeatureConfig.Builder(
                                    new SimpleBlockStateProvider(DesolationBlocks.CHARRED_LOG.getDefaultState()),
                                    new SimpleBlockStateProvider(DesolationBlocks.CHARRED_BRANCHES.getDefaultState()),
                                    new BlobFoliagePlacer(
                                            UniformIntDistribution.of(2),
                                            UniformIntDistribution.of(0),
                                            3),
                                    new StraightTrunkPlacer(6, 10, 1),
                                    new TwoLayersFeatureSize(1, 0, 1)
                            ))
                                    .ignoreVines()
                                    .build()

                    )
                    .decorate(net.minecraft.world.gen.feature.ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
                    .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(10, 0.1F, 1))),
                    "tree_charred")
    );

    public static final ConfiguredFeature<FeatureConfig, ?> PATCH_SCORCHED_TUFT = register(
            (ConfiguredFeature)Feature.RANDOM_PATCH
                    .configure(Configs.SCORCHED_TUFT_CONFIG)
                    .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
                    .repeat(5), "patch_scorched_tuft");

    public static final ConfiguredFeature<FeatureConfig, ?> PATCH_ASH_LAYER = register(
            (ConfiguredFeature)Feature.RANDOM_PATCH
                    .configure(Configs.ASH_LAYER_CONFIG)
                    .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
                    .repeat(3), "patch_ash_layer");

    public static final ConfiguredFeature<FeatureConfig, ?> PATCH_EMBER_CHUNK = register(
            (ConfiguredFeature)Feature.RANDOM_PATCH
                    .configure(Configs.EMBER_CHUNK_CONFIG)
                    .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
                    .repeat(4), "patch_ember_chunk");

    public static final ConfiguredFeature<FeatureConfig, ?> PATCH_ASH_BRAMBLE = register(
            (ConfiguredFeature)Feature.RANDOM_PATCH
                    .configure(Configs.ASH_BRAMBLE_CONFIG)
                    .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
                    .repeat(5), "patch_charred_branch");

    public static final ConfiguredFeature<FeatureConfig, ?> PLANT_CINDERFRUIT = register(
            (ConfiguredFeature)Feature.RANDOM_PATCH
                    .configure(Configs.PLANT_CINDERFRUIT_CONFIG)
                    .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
                    .repeat(1), "plant_cinderfruit");

    public static final class Configs {
        public static final RandomPatchFeatureConfig SCORCHED_TUFT_CONFIG;
        public static final RandomPatchFeatureConfig ASH_LAYER_CONFIG;
        public static final RandomPatchFeatureConfig EMBER_CHUNK_CONFIG;
        public static final RandomPatchFeatureConfig ASH_BRAMBLE_CONFIG;
        public static final RandomPatchFeatureConfig PLANT_CINDERFRUIT_CONFIG;

        static {
            SCORCHED_TUFT_CONFIG =
                    (new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(DesolationBlocks.SCORCHED_TUFT.getDefaultState()),
                            SimpleBlockPlacer.INSTANCE))
                            .tries(96)
                            .whitelist(Sets.newHashSet(DesolationBlocks.CHARRED_SOIL))
                            .build();
            ASH_LAYER_CONFIG = (new RandomPatchFeatureConfig.Builder(new WeightedBlockStateProvider()
                    .addState(DesolationBlocks.ASH_LAYER_BLOCK.getDefaultState().with(LAYERS, 1), 30)
                    .addState(DesolationBlocks.ASH_LAYER_BLOCK.getDefaultState().with(LAYERS, 2), 20)
                    .addState(DesolationBlocks.ASH_LAYER_BLOCK.getDefaultState().with(LAYERS, 3), 15)
                    .addState(DesolationBlocks.ASH_LAYER_BLOCK.getDefaultState().with(LAYERS, 4), 13)
                    .addState(DesolationBlocks.ASH_LAYER_BLOCK.getDefaultState().with(LAYERS, 5), 10)
                    .addState(DesolationBlocks.ASH_LAYER_BLOCK.getDefaultState().with(LAYERS, 6), 7)
                    .addState(DesolationBlocks.ASH_LAYER_BLOCK.getDefaultState().with(LAYERS, 7), 3)
                    .addState(DesolationBlocks.ASH_LAYER_BLOCK.getDefaultState().with(LAYERS, 8), 2),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(128)
                    .spreadX(11)
                    .spreadZ(11)
                    .whitelist(Sets.newHashSet(DesolationBlocks.CHARRED_BRANCHES, DesolationBlocks.CHARRED_LOG, DesolationBlocks.CHARRED_SOIL))
                    .build();
            EMBER_CHUNK_CONFIG = (new RandomPatchFeatureConfig.Builder(
                    new SimpleBlockStateProvider(DesolationBlocks.EMBER_BLOCK.getDefaultState()),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(3)
                    .spreadX(3)
                    .spreadY(1)
                    .spreadZ(3)
                    .whitelist(Sets.newHashSet(DesolationBlocks.CHARRED_SOIL))
                    .blacklist(Sets.newHashSet(Blocks.AIR.getDefaultState(), Blocks.WATER.getDefaultState()))
                    .canReplace()
                    .build();
            ASH_BRAMBLE_CONFIG = (new RandomPatchFeatureConfig.Builder(
                    new SimpleBlockStateProvider(DesolationBlocks.ASH_BRAMBLE.getDefaultState()),
                    /*BushFoliagePlacer(
                    UniformIntDistribution.of(2, 1),
                    UniformIntDistribution.of(0),
                    2*/
                    SimpleBlockPlacer.INSTANCE))
                    .tries(64)
                    .spreadX(8)
                    .spreadY(2)
                    .spreadZ(8)
                    .whitelist(Sets.newHashSet(DesolationBlocks.CHARRED_SOIL, DesolationBlocks.CHARRED_LOG,
                            DesolationBlocks.ASH_BRAMBLE))
                    .build();
            PLANT_CINDERFRUIT_CONFIG = (new RandomPatchFeatureConfig.Builder(
                    new SimpleBlockStateProvider(DesolationBlocks.CINDERFRUIT_PLANT.getDefaultState().with(AGE, 1)),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(1)
                    .whitelist(Sets.newHashSet(DesolationBlocks.CHARRED_SOIL))
                    .blacklist(Sets.newHashSet(Blocks.AIR.getDefaultState(), Blocks.WATER.getDefaultState()))
                    .build();
        }
    }

    static void init() {

    }

    public static ConfiguredFeature register(ConfiguredFeature feature, String path) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Desolation.id(path), feature);
    }
}
