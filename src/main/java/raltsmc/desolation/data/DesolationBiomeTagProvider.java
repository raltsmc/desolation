package raltsmc.desolation.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
import raltsmc.desolation.registry.DesolationBiomes;
import raltsmc.desolation.tag.DesolationBiomeTags;

import java.util.concurrent.CompletableFuture;

public class DesolationBiomeTagProvider extends FabricTagProvider<Biome> {
	protected DesolationBiomeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, RegistryKeys.BIOME, registriesFuture);
	}

	@Override
	public void configure(RegistryWrapper.WrapperLookup registries) {
		/*
		 * Vanilla biome categories
		 */
		getOrCreateTagBuilder(TagKey.of(RegistryKeys.BIOME, BiomeTags.IS_FOREST.id()))
			.addOptional(DesolationBiomes.CHARRED_FOREST)
			.addOptional(DesolationBiomes.CHARRED_FOREST_SMALL);


		/*
		 * Conventional biome categories
		 */
		getOrCreateTagBuilder(TagKey.of(RegistryKeys.BIOME, ConventionalBiomeTags.CLIMATE_TEMPERATE.id()))
			.addOptional(DesolationBiomes.CHARRED_FOREST)
			.addOptional(DesolationBiomes.CHARRED_FOREST_CLEARING)
			.addOptional(DesolationBiomes.CHARRED_FOREST_SMALL);

		getOrCreateTagBuilder(TagKey.of(RegistryKeys.BIOME, ConventionalBiomeTags.IN_OVERWORLD.id()))
			.addOptional(DesolationBiomes.CHARRED_FOREST)
			.addOptional(DesolationBiomes.CHARRED_FOREST_CLEARING)
			.addOptional(DesolationBiomes.CHARRED_FOREST_SMALL);


		/*
		 * Biome structure generation tags
		 */
		getOrCreateTagBuilder(TagKey.of(RegistryKeys.BIOME, BiomeTags.MINESHAFT_HAS_STRUCTURE.id()))
			.addOptional(DesolationBiomes.CHARRED_FOREST)
			.addOptional(DesolationBiomes.CHARRED_FOREST_CLEARING)
			.addOptional(DesolationBiomes.CHARRED_FOREST_SMALL);

		getOrCreateTagBuilder(TagKey.of(RegistryKeys.BIOME, BiomeTags.RUINED_PORTAL_STANDARD_HAS_STRUCTURE.id()))
			.addOptional(DesolationBiomes.CHARRED_FOREST)
			.addOptional(DesolationBiomes.CHARRED_FOREST_CLEARING)
			.addOptional(DesolationBiomes.CHARRED_FOREST_SMALL);

		getOrCreateTagBuilder(TagKey.of(RegistryKeys.BIOME, BiomeTags.STRONGHOLD_HAS_STRUCTURE.id()))
			.addOptional(DesolationBiomes.CHARRED_FOREST)
			.addOptional(DesolationBiomes.CHARRED_FOREST_CLEARING)
			.addOptional(DesolationBiomes.CHARRED_FOREST_SMALL);

		getOrCreateTagBuilder(DesolationBiomeTags.ASH_TINKER_BASE_HAS_STRUCTURE)
			.addOptional(DesolationBiomes.CHARRED_FOREST)
			.addOptional(DesolationBiomes.CHARRED_FOREST_CLEARING)
			.addOptional(DesolationBiomes.CHARRED_FOREST_SMALL);
	}
}
