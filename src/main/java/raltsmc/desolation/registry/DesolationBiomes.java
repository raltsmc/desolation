package raltsmc.desolation.registry;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.world.biome.BiomeCreator;

@SuppressWarnings("UnstableApiUsage")
public class DesolationBiomes {
    public static final RegistryKey<Biome> CHARRED_FOREST = RegistryKey.of(RegistryKeys.BIOME, Desolation.id("charred_forest"));
    public static final RegistryKey<Biome> CHARRED_FOREST_CLEARING = RegistryKey.of(RegistryKeys.BIOME, Desolation.id("charred_forest_clearing"));
    public static final RegistryKey<Biome> CHARRED_FOREST_SMALL = RegistryKey.of(RegistryKeys.BIOME, Desolation.id("charred_forest_small"));

    public static void populate(FabricDynamicRegistryProvider.Entries entries) {
        entries.add(CHARRED_FOREST, BiomeCreator.createCharredForest(entries, false, false));
        entries.add(CHARRED_FOREST_CLEARING, BiomeCreator.createCharredForest(entries, true, true));
        entries.add(CHARRED_FOREST_SMALL, BiomeCreator.createCharredForest(entries, false, true));
    }

    public static void init() { }
}
