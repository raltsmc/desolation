package raltsmc.desolation.world.feature;

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
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.world.gen.foliage.CharredFoliagePlacer;
import raltsmc.desolation.world.gen.trunk.FallenTrunkPlacer;
import raltsmc.desolation.world.gen.trunk.BasedTrunkPlacer;

import static net.minecraft.block.SnowBlock.LAYERS;
import static raltsmc.desolation.block.CinderfruitPlantBlock.AGE;

public final class DesolationConfiguredFeatures {

    public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_CHARRED = (
            register(
                    Feature.TREE.configure((new TreeFeatureConfig.Builder(
                                    new SimpleBlockStateProvider(DesolationBlocks.CHARRED_LOG.getDefaultState()),
                                    new SimpleBlockStateProvider(DesolationBlocks.CHARRED_BRANCHES.getDefaultState()),
                                    new CharredFoliagePlacer(
                                            UniformIntDistribution.of(4),
                                            UniformIntDistribution.of(0),
                                            3),
                                    new BasedTrunkPlacer(6, 10, 1),
                                    new TwoLayersFeatureSize(1, 0, 1)
                            ))
                                    .ignoreVines()
                                    .build()

                    )
                    .decorate(net.minecraft.world.gen.feature.ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
                    .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(15, 0.1F, 1))),
                    "tree_charred")
    );

    public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_CHARRED_SMALL = (
            register(
                    Feature.TREE.configure((new TreeFeatureConfig.Builder(
                                    new SimpleBlockStateProvider(DesolationBlocks.CHARRED_LOG.getDefaultState()),
                                    new SimpleBlockStateProvider(DesolationBlocks.CHARRED_BRANCHES.getDefaultState()),
                                    new CharredFoliagePlacer(
                                            UniformIntDistribution.of(2),
                                            UniformIntDistribution.of(0),
                                            2),
                                    new StraightTrunkPlacer(4, 2, 0),
                                    new TwoLayersFeatureSize(1, 0, 1)
                            ))
                                    .ignoreVines()
                                    .build()

                    )
                            .decorate(net.minecraft.world.gen.feature.ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
                            .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(10, 0.1F, 1))),
                    "tree_charred_small")
    );

    public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_CHARRED_FALLEN = (
            register(
                    Feature.TREE.configure((new TreeFeatureConfig.Builder(
                                    new SimpleBlockStateProvider(DesolationBlocks.CHARRED_LOG.getDefaultState()),
                                    new SimpleBlockStateProvider(DesolationBlocks.CHARRED_BRANCHES.getDefaultState()),
                                    new BlobFoliagePlacer(
                                            UniformIntDistribution.of(0),
                                            UniformIntDistribution.of(0),
                                            0),
                                    new FallenTrunkPlacer(6, 10, 1),
                                    new TwoLayersFeatureSize(0, 0, 0)
                            ))
                                    .ignoreVines()
                                    .build()

                    )
                            .decorate(net.minecraft.world.gen.feature.ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
                            .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(1, 0.1F, 1))),
                    "tree_charred_fallen")
    );

    public static final ConfiguredFeature<TreeFeatureConfig, ?> TREE_CHARRED_FALLEN_SMALL = (
            register(
                    Feature.TREE.configure((new TreeFeatureConfig.Builder(
                                    new SimpleBlockStateProvider(DesolationBlocks.CHARRED_LOG.getDefaultState()),
                                    new SimpleBlockStateProvider(DesolationBlocks.CHARRED_BRANCHES.getDefaultState()),
                                    new BlobFoliagePlacer(
                                            UniformIntDistribution.of(0),
                                            UniformIntDistribution.of(0),
                                            0),
                                    new FallenTrunkPlacer(4, 2, 0),
                                    new TwoLayersFeatureSize(0, 0, 0)
                            ))
                                    .ignoreVines()
                                    .build()

                    )
                            .decorate(net.minecraft.world.gen.feature.ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
                            .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(1, 0.1F, 1))),
                    "tree_charred_fallen_small")
    );

    public static final ConfiguredFeature<FeatureConfig, ?> PATCH_SCORCHED_TUFT = register(
            (ConfiguredFeature)Feature.RANDOM_PATCH
                    .configure(Configs.SCORCHED_TUFT_CONFIG)
                    .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
                    .repeat(8), "patch_scorched_tuft");

    public static final ConfiguredFeature<FeatureConfig, ?> PATCH_ASH_LAYER = register(
            (ConfiguredFeature)Feature.RANDOM_PATCH
                    .configure(Configs.ASH_LAYER_CONFIG)
                    .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
                    .repeat(3), "patch_ash_layer");

    public static final ConfiguredFeature<FeatureConfig, ?> PATCH_EMBER_CHUNK = register(
            (ConfiguredFeature)DesolationFeatures.SCATTERED
                    .configure(Configs.EMBER_CHUNK_CONFIG)
                    .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
                    .repeat(8), "patch_ember_chunk");

    public static final ConfiguredFeature<FeatureConfig, ?> PATCH_ASH_BRAMBLE = register(
            (ConfiguredFeature)Feature.RANDOM_PATCH
                    .configure(Configs.ASH_BRAMBLE_CONFIG)
                    .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
                    .repeat(5), "patch_ash_bramble");

    public static final ConfiguredFeature<FeatureConfig, ?> PLANT_CINDERFRUIT = register(
            (ConfiguredFeature)Feature.RANDOM_PATCH
                    .configure(Configs.PLANT_CINDERFRUIT_CONFIG)
                    .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE)
                    .repeat(1), "plant_cinderfruit");

    public static final ConfiguredFeature<?, ?> GIANT_BOULDER = register(
            (ConfiguredFeature)DesolationFeatures.GIANT_BOULDER
                    .configure(new SingleStateFeatureConfig(Blocks.STONE.getDefaultState()))
                    .decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP)
                    .repeatRandomly(1), "giant_boulder");

    public static final class Configs {
        public static final RandomPatchFeatureConfig SCORCHED_TUFT_CONFIG;
        public static final RandomPatchFeatureConfig ASH_LAYER_CONFIG;
        public static final ScatteredFeatureConfig EMBER_CHUNK_CONFIG;
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
            /*EMBER_CHUNK_CONFIG = (new RandomPatchFeatureConfig.Builder(
                    new SimpleBlockStateProvider(DesolationBlocks.EMBER_BLOCK.getDefaultState()),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(3)
                    .spreadX(3)
                    .spreadY(1)
                    .spreadZ(3)
                    .whitelist(Sets.newHashSet(DesolationBlocks.CHARRED_SOIL))
                    .blacklist(Sets.newHashSet(Blocks.AIR.getDefaultState(), Blocks.WATER.getDefaultState()))
                    .canReplace()
                    .build();*/
            EMBER_CHUNK_CONFIG = (new ScatteredFeatureConfig.Builder(
                    new SimpleBlockStateProvider(DesolationBlocks.EMBER_BLOCK.getDefaultState()),
                    SimpleBlockPlacer.INSTANCE))
                    .tries(5)
                    .spreadX(3)
                    .spreadY(0)
                    .spreadZ(3)
                    .whitelist(Sets.newHashSet(DesolationBlocks.CHARRED_SOIL, DesolationBlocks.EMBER_BLOCK))
                    .canReplace()
                    .modifyGround()
                    .build();
            ASH_BRAMBLE_CONFIG = (new RandomPatchFeatureConfig.Builder(
                    new SimpleBlockStateProvider(DesolationBlocks.ASH_BRAMBLE.getDefaultState()),
                    /*BushFoliagePlacer(
                    UniformIntDistribution.of(2, 1),
                    UniformIntDistribution.of(0),
                    2*/
                    SimpleBlockPlacer.INSTANCE))
                    .tries(8)
                    .spreadX(6)
                    .spreadY(2)
                    .spreadZ(6)
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

    public static void init() {

    }

    public static ConfiguredFeature register(ConfiguredFeature feature, String path) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Desolation.id(path), feature);
    }
}
