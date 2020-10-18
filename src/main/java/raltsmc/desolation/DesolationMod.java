package raltsmc.desolation;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import org.apache.commons.lang3.ArrayUtils;
import raltsmc.desolation.mixin.BuiltinBiomesAccessor;
import raltsmc.desolation.mixin.SetBaseBiomesLayerAccessor;
import raltsmc.desolation.mixin.VanillaLayeredBiomeSourceAccessor;
import raltsmc.desolation.registry.*;

import java.util.ArrayList;
import java.util.List;

public class DesolationMod implements ModInitializer {

	public static final String MODID = "desolation";

	public static final ItemGroup DSL_GROUP = FabricItemGroupBuilder.build(
			new Identifier("desolation", "dsl_group"),
			() -> new ItemStack(DesolationBlocks.EMBER_BLOCK));

	private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CHARRED_SURFACE_BUILDER =
			SurfaceBuilder.DEFAULT.withConfig(new TernarySurfaceConfig(
					DesolationBlocks.CHARRED_SOIL.getDefaultState(),
					DesolationBlocks.CHARRED_SOIL.getDefaultState(),
					DesolationBlocks.CHARRED_SOIL.getDefaultState()
			));

	private static final Biome CHARRED_FOREST = createCharredForest();

	private static Biome createCharredForest() {
		SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
		spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(DesolationEntities.ASH_SCUTTLER, 12, 1, 2));
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
		generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, DesolationStructures.TREE_CHARRED);
		generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, DesolationStructures.TREE_CHARRED_FALLEN);
		generationSettings.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, DesolationStructures.PATCH_ASH_LAYER);
		generationSettings.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, DesolationStructures.PATCH_EMBER_CHUNK);
		generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, DesolationStructures.PATCH_SCORCHED_TUFT);
		generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, DesolationStructures.PATCH_ASH_BRAMBLE);
		generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, DesolationStructures.PLANT_CINDERFRUIT);

		return (new Biome.Builder())
				.precipitation(Biome.Precipitation.NONE)
				.category(Biome.Category.NONE)
				.depth(0.125F)
				.scale(0.37F)
				.temperature(0.9F)
				.downfall(0.1F)
				.effects((new BiomeEffects.Builder())
						.waterColor(0x5b646e)
						.waterFogColor(0x2a3036)
						.fogColor(0xb5b5b5)
						.skyColor(0xa1aab3)
						.particleConfig(new BiomeParticleConfig(ParticleTypes.WHITE_ASH, 0.118093334F))
						.loopSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
						.moodSound(new BiomeMoodSound(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 6000, 8, 2.0D))
						.build())
				.spawnSettings(spawnSettings.spawnCost(DesolationEntities.ASH_SCUTTLER, 0.5D, 0.5D).build())
				.generationSettings(generationSettings.build())
				.build();
	}

	public static final RegistryKey<Biome> CHARRED_FOREST_KEY = RegistryKey.of(Registry.BIOME_KEY, Desolation.id(
			"charred_forest"));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		DesolationRegistries.init();
		Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, Desolation.id("charred"),
				CHARRED_SURFACE_BUILDER);
		Registry.register(BuiltinRegistries.BIOME, CHARRED_FOREST_KEY.getValue(), CHARRED_FOREST);
		BuiltinBiomesAccessor.getRawIdMap().put(BuiltinRegistries.BIOME.getRawId(CHARRED_FOREST), CHARRED_FOREST_KEY);

		List<RegistryKey<Biome>> biomes = new ArrayList<>(VanillaLayeredBiomeSourceAccessor.getBiomes());
		biomes.add(CHARRED_FOREST_KEY);
		VanillaLayeredBiomeSourceAccessor.setBiomes(biomes);

		SetBaseBiomesLayerAccessor.setTemperateBiomes(
				ArrayUtils.add(SetBaseBiomesLayerAccessor.getTemperateBiomes(),
						BuiltinRegistries.BIOME.getRawId(CHARRED_FOREST))
		);

		FuelRegistry.INSTANCE.add(DesolationItems.CHARCOAL_BIT, 400);

		System.out.println("Desolation initialized!");
	}
}
