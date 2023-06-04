package raltsmc.desolation.world.biome;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import raltsmc.desolation.registry.DesolationEntities;
import raltsmc.desolation.world.feature.DesolationPlacedFeatures;

@SuppressWarnings("UnstableApiUsage")
public class BiomeCreator {
    public static Biome createCharredForest(FabricDynamicRegistryProvider.Entries entries, boolean isClearing, boolean isSmall) {
        return new Biome.Builder()
                .generationSettings(createGenerationSettings(entries, isClearing, isSmall))
                .spawnSettings(createSpawnSettings())
                .precipitation(false)
                .temperature(0.9F)
                .downfall(0.1F)
                .effects((new BiomeEffects.Builder())
                        .waterColor(0x5b646e)
                        .waterFogColor(0x2a3036)
                        .fogColor(0xb5b5b5)
                        .skyColor(0xa1aab3)
                        .grassColor(0x342d2f)
                        .foliageColor(0x443d3f)
                        .particleConfig(new BiomeParticleConfig(ParticleTypes.WHITE_ASH, 0.118093334F))
                        .loopSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
                        .moodSound(new BiomeMoodSound(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 6000, 8, 2.0D))
                        .build()
                )
                .build();
    }

    private static GenerationSettings createGenerationSettings(FabricDynamicRegistryProvider.Entries entries, boolean isClearing, boolean isSmall) {
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(entries.placedFeatures(), entries.configuredCarvers());

        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addDefaultOres(generationSettings);
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        if (isSmall) {
            if (!isClearing) {
                generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, entries.ref(DesolationPlacedFeatures.TREES_CHARRED_SMALL));
            }
            generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, entries.ref(DesolationPlacedFeatures.TREES_CHARRED_FALLEN_SMALL));
        } else {
            if (!isClearing) {
                generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, entries.ref(DesolationPlacedFeatures.TREES_CHARRED_LARGE));
            }
            generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, entries.ref(DesolationPlacedFeatures.PATCH_CHARRED_SAPLING));
            generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, entries.ref(DesolationPlacedFeatures.TREES_CHARRED_FALLEN_LARGE));
        }
        generationSettings.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, entries.ref(DesolationPlacedFeatures.PATCH_ASH_LAYER));
        generationSettings.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, entries.ref(DesolationPlacedFeatures.PATCH_EMBER_CHUNK));
        generationSettings.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, entries.ref(DesolationPlacedFeatures.GIANT_BOULDER));
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, entries.ref(DesolationPlacedFeatures.PATCH_SCORCHED_TUFT));
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, entries.ref(DesolationPlacedFeatures.PATCH_ASH_BRAMBLE));
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, entries.ref(DesolationPlacedFeatures.PLANT_CINDERFRUIT));

        return generationSettings.build();
    }

    private static SpawnSettings createSpawnSettings() {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        spawnSettings.spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(DesolationEntities.ASH_SCUTTLER, 1, 1, 2));
        spawnSettings.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(DesolationEntities.BLACKENED, 1, 1, 3));

        return spawnSettings.build();
    }
}
