package raltsmc.desolation.world.biome;

import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import raltsmc.desolation.registry.DesolationBlocks;

public class BiomeCreator {
    private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CHARRED_SURFACE_BUILDER =
            SurfaceBuilder.DEFAULT.withConfig(new TernarySurfaceConfig(
                    DesolationBlocks.ASH_BLOCK.getDefaultState(),
                    DesolationBlocks.EMBER_BLOCK.getDefaultState(),
                    Blocks.DIRT.getDefaultState()
            ));

    private static final Biome CHARRED_FOREST = createCharredForest();

    private static Biome createCharredForest() {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        DefaultBiomeFeatures.addFarmAnimals(spawnSettings);
        DefaultBiomeFeatures.addMonsters(spawnSettings, 5, 5, 100);

        GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
        generationSettings.surfaceBuilder(CHARRED_SURFACE_BUILDER);
        DefaultBiomeFeatures.addDefaultUndergroundStructures(generationSettings);
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addDefaultLakes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addDefaultOres(generationSettings);
        DefaultBiomeFeatures.addDefaultDisks(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        return (new Biome.Builder())
                .precipitation(Biome.Precipitation.NONE)
                .category(Biome.Category.NONE)
                .depth(0.125F)
                .scale(0.08F)
                .temperature(0.9F)
                .downfall(0.1F)
                .effects((new BiomeEffects.Builder())
                        .waterColor(0x5b646e)
                        .waterFogColor(0x2a3036)
                        .fogColor(0xb5b5b5)
                        .skyColor(0xd1dae3)
                        .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }
}
