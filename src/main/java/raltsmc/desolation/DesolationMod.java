package raltsmc.desolation;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.fabricmc.fabric.mixin.biome.BuiltinBiomesAccessor;
import net.fabricmc.fabric.mixin.biome.VanillaLayeredBiomeSourceAccessor;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;
import org.apache.commons.lang3.ArrayUtils;
import raltsmc.desolation.mixin.SetBaseBiomesLayerAccessor;
import raltsmc.desolation.registry.*;
import raltsmc.desolation.structure.AshTinkerBaseGenerator;
import raltsmc.desolation.world.feature.AshTinkerBaseFeature;
import raltsmc.desolation.world.feature.DesolationConfiguredFeatures;
import software.bernie.geckolib3.GeckoLib;

import java.util.ArrayList;
import java.util.List;

public class DesolationMod implements ModInitializer {

	public static final String MODID = "desolation";

	public static final ItemGroup DSL_GROUP = FabricItemGroupBuilder.build(
			new Identifier("desolation", "dsl_group"),
			() -> new ItemStack(DesolationBlocks.EMBER_BLOCK));

	public static final StructurePieceType TINKER_BASE_PIECE = AshTinkerBaseGenerator.Piece::new;
	private static final StructureFeature<DefaultFeatureConfig> TINKER_BASE =
			new AshTinkerBaseFeature(DefaultFeatureConfig.CODEC);
	public static final ConfiguredStructureFeature<?, ?> TINKER_BASE_CONFIGURED =
			TINKER_BASE.configure(DefaultFeatureConfig.DEFAULT);

	private static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> CHARRED_SURFACE_BUILDER =
			SurfaceBuilder.DEFAULT.withConfig(new TernarySurfaceConfig(
					DesolationBlocks.CHARRED_SOIL.getDefaultState(),
					DesolationBlocks.CHARRED_SOIL.getDefaultState(),
					DesolationBlocks.CHARRED_SOIL.getDefaultState()
			));

	private static final Biome CHARRED_FOREST = createCharredForest(false, false);
	private static final Biome CHARRED_FOREST_CLEARING = createCharredForest(true, true);
	private static final Biome CHARRED_FOREST_SMALL = createCharredForest(false, true);

	private static Biome createCharredForest(boolean isClearing, boolean isSmall) {
		SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
		spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(DesolationEntities.ASH_SCUTTLER, 4, 2, 3));
		spawnSettings.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(DesolationEntities.BLACKENED, 1, 1, 3));

		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
		generationSettings.surfaceBuilder(CHARRED_SURFACE_BUILDER);
		DefaultBiomeFeatures.addDefaultUndergroundStructures(generationSettings);
		DefaultBiomeFeatures.addLandCarvers(generationSettings);
		DefaultBiomeFeatures.addDungeons(generationSettings);
		DefaultBiomeFeatures.addMineables(generationSettings);
		DefaultBiomeFeatures.addDefaultOres(generationSettings);
		DefaultBiomeFeatures.addDefaultDisks(generationSettings);
		DefaultBiomeFeatures.addSprings(generationSettings);
		DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
		if (isSmall) {
			if (!isClearing) {
				generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, DesolationConfiguredFeatures.TREE_CHARRED_SMALL);
			}
			generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, DesolationConfiguredFeatures.TREE_CHARRED_FALLEN_SMALL);
		} else {
			if (!isClearing) {
				generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, DesolationConfiguredFeatures.TREE_CHARRED);
			}
			generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, DesolationConfiguredFeatures.TREE_CHARRED_FALLEN);
		}
		generationSettings.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, DesolationConfiguredFeatures.PATCH_ASH_LAYER);
		generationSettings.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, DesolationConfiguredFeatures.PATCH_EMBER_CHUNK);
		generationSettings.feature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, DesolationConfiguredFeatures.GIANT_BOULDER);
		generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, DesolationConfiguredFeatures.PATCH_SCORCHED_TUFT);
		generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, DesolationConfiguredFeatures.PATCH_ASH_BRAMBLE);
		generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, DesolationConfiguredFeatures.PLANT_CINDERFRUIT);
		generationSettings.structureFeature(TINKER_BASE_CONFIGURED);

		return (new Biome.Builder())
				.precipitation(Biome.Precipitation.NONE)
				.category(Biome.Category.NONE)
				.depth(0.125F)
				.scale(0.22F)
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
						.build())
				//.spawnSettings(spawnSettings.spawnCost(DesolationEntities.ASH_SCUTTLER, 0.5D, 0.5D).build())
				.spawnSettings(spawnSettings.build())
				.generationSettings(generationSettings.build())
				.build();
	}

	public static final RegistryKey<Biome> CHARRED_FOREST_KEY = RegistryKey.of(Registry.BIOME_KEY, Desolation.id("charred_forest"));
	public static final RegistryKey<Biome> CHARRED_FOREST_CLEARING_KEY = RegistryKey.of(Registry.BIOME_KEY, Desolation.id("charred_forest_clearing"));
	public static final RegistryKey<Biome> CHARRED_FOREST_SMALL_KEY = RegistryKey.of(Registry.BIOME_KEY, Desolation.id("charred_forest_small"));

	@Override
	public void onInitialize() {
		DesolationRegistries.init();

		GeckoLib.initialize();

		Registry.register(Registry.STRUCTURE_PIECE, Desolation.id("tinker_base_piece"), TINKER_BASE_PIECE);
		FabricStructureBuilder.create(Desolation.id("tinker_base"), TINKER_BASE)
				.step(GenerationStep.Feature.SURFACE_STRUCTURES)
				.defaultConfig(32, 8, 12345)
				// TODO have support beams extend down so this isnt necessary
				.adjustsSurface()
				.register();

		BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, Desolation.id("tinker_base"), TINKER_BASE_CONFIGURED);

		Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, Desolation.id("charred"), CHARRED_SURFACE_BUILDER);
		Registry.register(BuiltinRegistries.BIOME, CHARRED_FOREST_KEY.getValue(), CHARRED_FOREST);
		Registry.register(BuiltinRegistries.BIOME, CHARRED_FOREST_CLEARING_KEY.getValue(), CHARRED_FOREST_CLEARING);
		Registry.register(BuiltinRegistries.BIOME, CHARRED_FOREST_SMALL_KEY.getValue(), CHARRED_FOREST_SMALL);

		BuiltinBiomesAccessor.getBY_RAW_ID().put(BuiltinRegistries.BIOME.getRawId(CHARRED_FOREST), CHARRED_FOREST_KEY);
		BuiltinBiomesAccessor.getBY_RAW_ID().put(BuiltinRegistries.BIOME.getRawId(CHARRED_FOREST_CLEARING), CHARRED_FOREST_CLEARING_KEY);
		BuiltinBiomesAccessor.getBY_RAW_ID().put(BuiltinRegistries.BIOME.getRawId(CHARRED_FOREST_SMALL), CHARRED_FOREST_SMALL_KEY);

		List<RegistryKey<Biome>> biomes = new ArrayList<>(VanillaLayeredBiomeSourceAccessor.getBIOMES());
		biomes.add(CHARRED_FOREST_KEY);
		biomes.add(CHARRED_FOREST_CLEARING_KEY);
		biomes.add(CHARRED_FOREST_SMALL_KEY);
		VanillaLayeredBiomeSourceAccessor.setBIOMES(biomes);

		/*SetBaseBiomesLayerAccessor.setTemperateBiomes(
				ArrayUtils.add(SetBaseBiomesLayerAccessor.getTemperateBiomes(),
						BuiltinRegistries.BIOME.getRawId(CHARRED_FOREST))
		);*/

		OverworldBiomes.addBiomeVariant(BiomeKeys.FOREST, CHARRED_FOREST_SMALL_KEY, 0.03D);
		OverworldBiomes.addBiomeVariant(BiomeKeys.BIRCH_FOREST, CHARRED_FOREST_SMALL_KEY, 0.02D);
		OverworldBiomes.addBiomeVariant(BiomeKeys.TALL_BIRCH_FOREST, CHARRED_FOREST_KEY, 0.07D);
		OverworldBiomes.addBiomeVariant(BiomeKeys.FOREST, CHARRED_FOREST_KEY, 0.02D);
		OverworldBiomes.addBiomeVariant(BiomeKeys.TAIGA, CHARRED_FOREST_KEY, 0.02D);
		OverworldBiomes.addHillsBiome(CHARRED_FOREST_KEY, CHARRED_FOREST_CLEARING_KEY, 0.07D);
		OverworldBiomes.addHillsBiome(CHARRED_FOREST_SMALL_KEY, CHARRED_FOREST_CLEARING_KEY, 0.05D);
		/*OverworldBiomes.addBiomeVariant(CHARRED_FOREST_KEY, CHARRED_FOREST_SMALL_KEY, 0.3D);
		OverworldBiomes.addHillsBiome(CHARRED_FOREST_KEY, CHARRED_FOREST_CLEARING_KEY, 0.05D);*/

		FuelRegistry.INSTANCE.add(DesolationItems.CHARCOAL_BIT, 400);

		System.out.println("Desolation initialized!");
	}
}
