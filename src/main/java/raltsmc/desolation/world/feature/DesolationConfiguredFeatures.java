package raltsmc.desolation.world.feature;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.world.gen.foliage.CharredFoliagePlacer;
import raltsmc.desolation.world.gen.trunk.BasedTrunkPlacer;
import raltsmc.desolation.world.gen.trunk.FallenTrunkPlacer;

import java.util.List;

import static net.minecraft.block.SnowBlock.LAYERS;
import static raltsmc.desolation.block.CinderfruitPlantBlock.AGE;

public final class DesolationConfiguredFeatures {
    public static RegistryKey<ConfiguredFeature<?, ?>> TREE_CHARRED = createRegistryKey("tree_charred");
    public static RegistryKey<ConfiguredFeature<?, ?>> TREE_CHARRED_SMALL = createRegistryKey("tree_charred_small");
    public static RegistryKey<ConfiguredFeature<?, ?>> TREE_CHARRED_FALLEN = createRegistryKey("tree_charred_fallen");
    public static RegistryKey<ConfiguredFeature<?, ?>> TREE_CHARRED_FALLEN_SMALL = createRegistryKey("tree_charred_fallen_small");

    public static RegistryKey<ConfiguredFeature<?, ?>> PATCH_CHARRED_SAPLING = createRegistryKey("patch_charred_sapling");
    public static RegistryKey<ConfiguredFeature<?, ?>> PATCH_SCORCHED_TUFT = createRegistryKey("patch_scorched_tuft");
    public static RegistryKey<ConfiguredFeature<?, ?>> PATCH_ASH_LAYER = createRegistryKey("patch_ash_layer");
    public static RegistryKey<ConfiguredFeature<?, ?>> PATCH_EMBER_CHUNK = createRegistryKey("patch_ember_chunk");
    public static RegistryKey<ConfiguredFeature<?, ?>> PATCH_ASH_BRAMBLE = createRegistryKey("patch_ash_bramble");
    public static RegistryKey<ConfiguredFeature<?, ?>> PLANT_CINDERFRUIT = createRegistryKey("plant_cinderfruit");
    public static RegistryKey<ConfiguredFeature<?, ?>> GIANT_BOULDER = createRegistryKey("giant_boulder");


    public static void populate(FabricDynamicRegistryProvider.Entries entries) {
        entries.add(TREE_CHARRED, configureFeature(Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(DesolationBlocks.CHARRED_LOG.getDefaultState()),
                new BasedTrunkPlacer(6, 10, 1),
                BlockStateProvider.of(DesolationBlocks.CHARRED_BRANCHES.getDefaultState()),
                new CharredFoliagePlacer(UniformIntProvider.create(3, 5), ConstantIntProvider.create(0),
                        UniformIntProvider.create(3, 4)),
                new TwoLayersFeatureSize(1, 0, 1))
                .ignoreVines()
                .build()));

        entries.add(TREE_CHARRED_SMALL, configureFeature(Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(DesolationBlocks.CHARRED_LOG.getDefaultState()),
                new StraightTrunkPlacer(4, 2, 0),
                BlockStateProvider.of(DesolationBlocks.CHARRED_BRANCHES.getDefaultState()),
                new CharredFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0),
                        ConstantIntProvider.create(2)),
                new TwoLayersFeatureSize(1, 0, 1))
                .ignoreVines()
                .build()));

        entries.add(TREE_CHARRED_FALLEN, configureFeature(Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(DesolationBlocks.CHARRED_LOG.getDefaultState()),
                new FallenTrunkPlacer(6, 10, 1),
                BlockStateProvider.of(DesolationBlocks.CHARRED_BRANCHES.getDefaultState()),
                new BlobFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), 0),
                new TwoLayersFeatureSize(0,0,0))
                .ignoreVines()
                .build()));

        entries.add(TREE_CHARRED_FALLEN_SMALL, configureFeature(Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(DesolationBlocks.CHARRED_LOG.getDefaultState()),
                new FallenTrunkPlacer(4, 2, 0),
                BlockStateProvider.of(DesolationBlocks.CHARRED_BRANCHES.getDefaultState()),
                new BlobFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), 0),
                new TwoLayersFeatureSize(0,0,0))
                .ignoreVines()
                .build()));

        entries.add(PATCH_CHARRED_SAPLING, configureFeature(Feature.RANDOM_PATCH, Configs.CHARRED_SAPLING_CONFIG));

        entries.add(PATCH_SCORCHED_TUFT, configureFeature(Feature.RANDOM_PATCH, Configs.SCORCHED_TUFT_CONFIG));

        entries.add(PATCH_ASH_LAYER, configureFeature(Feature.RANDOM_PATCH, Configs.ASH_LAYER_CONFIG));

        entries.add(PATCH_EMBER_CHUNK, configureFeature(DesolationFeatures.SCATTERED, Configs.EMBER_CHUNK_CONFIG));

        entries.add(PATCH_ASH_BRAMBLE, configureFeature(Feature.RANDOM_PATCH, Configs.ASH_BRAMBLE_CONFIG));

        entries.add(PLANT_CINDERFRUIT, configureFeature(DesolationFeatures.SCATTERED, Configs.PLANT_CINDERFRUIT_CONFIG));

        entries.add(GIANT_BOULDER, configureFeature(DesolationFeatures.GIANT_BOULDER, Configs.GIANT_BOULDER_CONFIG));
    }

    public static final class Configs {
        public static final RandomPatchFeatureConfig CHARRED_SAPLING_CONFIG = new RandomPatchFeatureConfig(1, 7, 3,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                        BlockStateProvider.of(DesolationBlocks.CHARRED_SAPLING)),
                        BlockPredicate.IS_AIR));

        public static final RandomPatchFeatureConfig SCORCHED_TUFT_CONFIG = new RandomPatchFeatureConfig(96, 7, 3,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                        BlockStateProvider.of(DesolationBlocks.SCORCHED_TUFT)),
                        BlockPredicate.IS_AIR));

        public static final RandomPatchFeatureConfig ASH_LAYER_CONFIG = new RandomPatchFeatureConfig(128, 11, 3,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                        new WeightedBlockStateProvider(new DataPool.Builder<BlockState>()
                                .add(DesolationBlocks.ASH_LAYER_BLOCK.getDefaultState().with(LAYERS, 1), 30)
                                .add(DesolationBlocks.ASH_LAYER_BLOCK.getDefaultState().with(LAYERS, 2), 20)
                                .add(DesolationBlocks.ASH_LAYER_BLOCK.getDefaultState().with(LAYERS, 3), 15)
                                .add(DesolationBlocks.ASH_LAYER_BLOCK.getDefaultState().with(LAYERS, 4), 13)
                                .add(DesolationBlocks.ASH_LAYER_BLOCK.getDefaultState().with(LAYERS, 5), 10)
                                .add(DesolationBlocks.ASH_LAYER_BLOCK.getDefaultState().with(LAYERS, 6), 7)
                                .add(DesolationBlocks.ASH_LAYER_BLOCK.getDefaultState().with(LAYERS, 7), 3)
                                .add(DesolationBlocks.ASH_LAYER_BLOCK.getDefaultState().with(LAYERS, 8), 2)
                                .build())),
                        BlockPredicate.IS_AIR));

        public static final ScatteredFeatureConfig EMBER_CHUNK_CONFIG = new ScatteredFeatureConfig.Builder(
                new WeightedBlockStateProvider(new DataPool.Builder<BlockState>()
                        .add(DesolationBlocks.EMBER_BLOCK.getDefaultState(), 1)
                        .add(DesolationBlocks.COOLED_EMBER_BLOCK.getDefaultState(), 1)
                        .build()))
                .tries(5)
                .spreadX(3)
                .spreadY(0)
                .spreadZ(3)
                .whitelist(List.of(DesolationBlocks.CHARRED_SOIL.getDefaultState(),
                        DesolationBlocks.EMBER_BLOCK.getDefaultState(),
                        DesolationBlocks.COOLED_EMBER_BLOCK.getDefaultState()))
                .canReplace()
                .modifyGround()
                .build();

        public static final RandomPatchFeatureConfig ASH_BRAMBLE_CONFIG = new RandomPatchFeatureConfig(8, 6, 2,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(
                        BlockStateProvider.of(DesolationBlocks.ASH_BRAMBLE)),
                        BlockPredicate.IS_AIR));

        public static final ScatteredFeatureConfig PLANT_CINDERFRUIT_CONFIG = new ScatteredFeatureConfig.Builder(
                BlockStateProvider.of(DesolationBlocks.CINDERFRUIT_PLANT.getDefaultState().with(AGE, 1)))
                .tries(1)
                .spreadX(0)
                .spreadY(0)
                .spreadZ(0)
                .failChance(0.92D)
                .whitelist(List.of(DesolationBlocks.CHARRED_SOIL.getDefaultState()))
                .blacklist(List.of(Blocks.AIR.getDefaultState(), Blocks.WATER.getDefaultState()))
                .build();

        public static final SingleStateFeatureConfig GIANT_BOULDER_CONFIG = new SingleStateFeatureConfig(Blocks.STONE.getDefaultState());
    }


    public static RegistryKey<ConfiguredFeature<?, ?>> createRegistryKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Desolation.id(name));
    }

    public static <FC extends FeatureConfig, F extends Feature<FC>> ConfiguredFeature<FC, ?> configureFeature(F feature, FC config) {
        return new ConfiguredFeature<>(feature, config);
    }

    public static void init() { }
}
