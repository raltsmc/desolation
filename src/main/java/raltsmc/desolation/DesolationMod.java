package raltsmc.desolation;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.fabricmc.fabric.mixin.biome.BuiltinBiomesAccessor;
import net.fabricmc.fabric.mixin.biome.VanillaLayeredBiomeSourceAccessor;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
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
import raltsmc.desolation.config.DesolationConfig;
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.registry.DesolationEntities;
import raltsmc.desolation.registry.DesolationItems;
import raltsmc.desolation.registry.DesolationRegistries;
import raltsmc.desolation.structure.AshTinkerBaseGenerator;
import raltsmc.desolation.world.feature.AshTinkerBaseFeature;
import raltsmc.desolation.world.feature.DesolationConfiguredFeatures;
import software.bernie.geckolib3.GeckoLib;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DesolationMod implements ModInitializer {

	public static final String MODID = "desolation";
	public static final DesolationConfig CONFIG;

	public static final ItemGroup DSL_GROUP = FabricItemGroupBuilder.build(
			new Identifier("desolation", "dsl_group"),
			() -> new ItemStack(DesolationBlocks.EMBER_BLOCK));

	public static final Identifier CINDER_SOUL_READY_PACKET_ID = Desolation.id("cinder_soul_ready");
	public static final Identifier CINDER_SOUL_TICK_PACKET_ID = Desolation.id("cinder_soul_tick");
	public static final Identifier CINDER_SOUL_DO_CINDER_DASH = Desolation.id("do_cinder_dash");

	public static final Identifier MUSIC_DISC_ASHES_SOUND_ID = Desolation.id("music_disc.ashes");
	public static final SoundEvent MUSIC_DISC_ASHES_SOUND = new SoundEvent(MUSIC_DISC_ASHES_SOUND_ID);

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
				generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, DesolationConfiguredFeatures.TREES_CHARRED_SMALL);
			}
			generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, DesolationConfiguredFeatures.TREES_CHARRED_FALLEN_SMALL);
		} else {
			if (!isClearing) {
				generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, DesolationConfiguredFeatures.TREES_CHARRED_LARGE);
			}
			generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, DesolationConfiguredFeatures.TREES_CHARRED_FALLEN_LARGE);
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

		Registry.register(Registry.SOUND_EVENT, MUSIC_DISC_ASHES_SOUND_ID, MUSIC_DISC_ASHES_SOUND);

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

		double cfLargeChance = CONFIG.charredForestChance;
		double cfSmallChance = CONFIG.smallCharredForestChance;
		double cfClearingChance = CONFIG.charredForestClearingChance;
		boolean generateClearings = CONFIG.generateClearings;
		OverworldBiomes.addBiomeVariant(BiomeKeys.FOREST, CHARRED_FOREST_SMALL_KEY, cfSmallChance);
		OverworldBiomes.addBiomeVariant(BiomeKeys.BIRCH_FOREST, CHARRED_FOREST_SMALL_KEY, cfSmallChance);
		OverworldBiomes.addBiomeVariant(BiomeKeys.TALL_BIRCH_FOREST, CHARRED_FOREST_KEY, cfLargeChance);
		OverworldBiomes.addBiomeVariant(BiomeKeys.FOREST, CHARRED_FOREST_KEY, cfLargeChance);
		OverworldBiomes.addBiomeVariant(BiomeKeys.TAIGA, CHARRED_FOREST_KEY, cfLargeChance);
		if (generateClearings) {
			OverworldBiomes.addHillsBiome(CHARRED_FOREST_KEY, CHARRED_FOREST_CLEARING_KEY, cfClearingChance);
			OverworldBiomes.addHillsBiome(CHARRED_FOREST_SMALL_KEY, CHARRED_FOREST_CLEARING_KEY, cfClearingChance);
		}

		FuelRegistry.INSTANCE.add(DesolationItems.CHARCOAL_BIT, 400);

		ServerPlayNetworking.registerGlobalReceiver(CINDER_SOUL_TICK_PACKET_ID, (server, player, handler, buf, sender) -> {
			server.execute(() -> {
				ServerWorld world = (ServerWorld)player.world;
				Random random = new Random();
				double d = player.getX() - 0.25D + random.nextDouble() / 2;
				double e = player.getY();
				double f = player.getZ() - 0.25D + random.nextDouble() / 2;

				double g = random.nextDouble() * 0.6D - 0.3D;
				double h = random.nextDouble() * 6.0D / 16.0D;
				double i = (random.nextDouble() - 0.5D) / 5.0D;

				world.spawnParticles(ParticleTypes.FLAME, d + g, e + h, f + g, 1, 0d, 0.1d + i, 0d, .1);
				if (random.nextDouble() < 0.25) {
					world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.AMBIENT, .8F, 1F);
				}
			});
		});

		ServerPlayNetworking.registerGlobalReceiver(CINDER_SOUL_READY_PACKET_ID, (server, player, handler, buf, sender) -> {
			server.execute(() -> {
				ServerWorld world = (ServerWorld)player.world;
				Random random = new Random();
				List<Vec3d> points = new ArrayList<Vec3d>();

				double phi = Math.PI * (3. - Math.sqrt(5.));
				for (int i = 0; i <= 150; ++i) {
					double y = 1 - (i / (float) (250 - 1)) * 2;
					double radius = Math.sqrt(1 - y * y);
					double theta = phi * i;
					double x = Math.cos(theta) * radius;
					double z = Math.sin(theta) * radius;

					points.add(new Vec3d(player.getX() + x * 0.5, player.getY() + 1 + y, player.getZ() + z * 0.5));
				}

				for (Vec3d vec : points) {
					Vec3d vel = vec.subtract(player.getPos())
							.normalize()
							.multiply(0.12 + random.nextDouble() * 0.03)
							.add(player.getVelocity().multiply(1, 0.1, 1))
							.multiply(1.25, 1, 1.25);
					world.spawnParticles(ParticleTypes.FLAME, vec.x, vec.y, vec.z, 1, vel.x, vel.y, vel.z, .1);
				}
			});
		});

		ServerPlayNetworking.registerGlobalReceiver(CINDER_SOUL_DO_CINDER_DASH, (server, player, handler, buf, sender) -> {
			server.execute(() -> {
				ServerWorld world = (ServerWorld)player.world;
				world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.PLAYERS, 1F, 1.6F);
			});
		});

		System.out.println("Desolation initialized!");
	}

	static {
		CONFIG = AutoConfig.register(DesolationConfig.class, JanksonConfigSerializer::new).getConfig();
	}
}
