package raltsmc.desolation.world.feature;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.tag.DesolationBlockTags;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class DesolationPlacedFeatures {
    public static final RegistryKey<PlacedFeature> TREES_CHARRED_LARGE = createRegistryKey("trees_charred_large");
    public static final RegistryKey<PlacedFeature> TREES_CHARRED_SMALL = createRegistryKey("trees_charred_small");
    public static final RegistryKey<PlacedFeature> TREES_CHARRED_FALLEN_LARGE = createRegistryKey("trees_charred_fallen_large");
    public static final RegistryKey<PlacedFeature> TREES_CHARRED_FALLEN_SMALL = createRegistryKey("trees_charred_fallen_small");

    public static final RegistryKey<PlacedFeature> PATCH_CHARRED_SAPLING = createRegistryKey("patch_charred_sapling");
    public static final RegistryKey<PlacedFeature> PATCH_SCORCHED_TUFT = createRegistryKey("patch_scorched_tuft");
    public static final RegistryKey<PlacedFeature> PATCH_ASH_LAYER = createRegistryKey("patch_ash_layer");
    public static final RegistryKey<PlacedFeature> PATCH_EMBER_CHUNK = createRegistryKey("patch_ember_chunk");
    public static final RegistryKey<PlacedFeature> PATCH_ASH_BRAMBLE = createRegistryKey("patch_ash_bramble");
    public static final RegistryKey<PlacedFeature> PLANT_CINDERFRUIT = createRegistryKey("plant_cinderfruit");
    public static final RegistryKey<PlacedFeature> GIANT_BOULDER = createRegistryKey("giant_boulder");


    public static void populate(FabricDynamicRegistryProvider.Entries entries) {
        final BlockPredicate ON_CHARRED_SOIL = BlockPredicate.matchingBlocks(Direction.DOWN.getVector(), DesolationBlocks.CHARRED_SOIL);
        final BlockPredicate ON_SCORCHED_EARTH = BlockPredicate.matchingBlockTag(Direction.DOWN.getVector(), DesolationBlockTags.SCORCHED_EARTH);

        entries.add(TREES_CHARRED_LARGE, placeTreeFeature(entries, 15, ON_SCORCHED_EARTH, DesolationConfiguredFeatures.TREE_CHARRED));

        entries.add(TREES_CHARRED_SMALL, placeTreeFeature(entries, 10, ON_SCORCHED_EARTH, DesolationConfiguredFeatures.TREE_CHARRED_SMALL));

        entries.add(TREES_CHARRED_FALLEN_LARGE, placeTreeFeature(entries, 4, ON_SCORCHED_EARTH, DesolationConfiguredFeatures.TREE_CHARRED_FALLEN));

        entries.add(TREES_CHARRED_FALLEN_SMALL, placeTreeFeature(entries, 3, ON_SCORCHED_EARTH, DesolationConfiguredFeatures.TREE_CHARRED_FALLEN_SMALL));

        entries.add(PATCH_CHARRED_SAPLING, placeFeature(entries, DesolationConfiguredFeatures.PATCH_CHARRED_SAPLING,
                CountPlacementModifier.of(2),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BlockFilterPlacementModifier.of(ON_CHARRED_SOIL)));

        entries.add(PATCH_SCORCHED_TUFT, placeFeature(entries, DesolationConfiguredFeatures.PATCH_SCORCHED_TUFT,
                CountPlacementModifier.of(8),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BlockFilterPlacementModifier.of(ON_CHARRED_SOIL)));

        entries.add(PATCH_ASH_LAYER, placeFeature(entries, DesolationConfiguredFeatures.PATCH_ASH_LAYER,
                CountPlacementModifier.of(3),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BlockFilterPlacementModifier.of(ON_SCORCHED_EARTH)));

        entries.add(PATCH_EMBER_CHUNK, placeFeature(entries, DesolationConfiguredFeatures.PATCH_EMBER_CHUNK,
                CountPlacementModifier.of(4),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP));

        entries.add(PATCH_ASH_BRAMBLE, placeFeature(entries, DesolationConfiguredFeatures.PATCH_ASH_BRAMBLE,
                CountPlacementModifier.of(5),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BlockFilterPlacementModifier.of(BlockPredicate.matchingBlocks(Direction.DOWN.getVector(),
                        DesolationBlocks.CHARRED_SOIL,
                        DesolationBlocks.CHARRED_LOG,
                        DesolationBlocks.ASH_BRAMBLE))));

        entries.add(PLANT_CINDERFRUIT, placeFeature(entries, DesolationConfiguredFeatures.PLANT_CINDERFRUIT,
                CountPlacementModifier.of(1),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP));

        entries.add(GIANT_BOULDER, placeFeature(entries, DesolationConfiguredFeatures.GIANT_BOULDER,
                CountPlacementModifier.of(1),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP));
    }


    public static RegistryKey<PlacedFeature> createRegistryKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Desolation.id(name));
    }

    public static PlacedFeature placeTreeFeature(FabricDynamicRegistryProvider.Entries entries, int count, BlockPredicate predicate, RegistryKey<ConfiguredFeature<?, ?>> feature) {
        return placeFeature(entries, feature,
                PlacedFeatures.createCountExtraModifier(count, 0.1f, 1),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BlockFilterPlacementModifier.of(predicate));
    }

    private static PlacedFeature placeFeature(FabricDynamicRegistryProvider.Entries entries, RegistryKey<ConfiguredFeature<?, ?>> feature, PlacementModifier... placementModifiers) {
        List<PlacementModifier> list = new ArrayList<>(List.of(placementModifiers));
        list.add(BiomePlacementModifier.of());
        return placeFeature(entries, feature, list);
    }
    private static PlacedFeature placeFeature(FabricDynamicRegistryProvider.Entries entries, RegistryKey<ConfiguredFeature<?, ?>> feature, List<PlacementModifier> list) {
        return new PlacedFeature(entries.ref(feature), list);
    }

    public static void init() { }
}
