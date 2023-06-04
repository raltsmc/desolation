package raltsmc.desolation.tag;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import raltsmc.desolation.Desolation;

public final class DesolationBiomeTags {
	public static final TagKey<Biome> ASH_TINKER_BASE_HAS_STRUCTURE = DesolationBiomeTags.of("ash_tinker_base_has_structure");

	@SuppressWarnings("UnnecessaryReturnStatement")
	private DesolationBiomeTags() {
		return;
	}

	private static TagKey<Biome> of(String path) {
		return DesolationBiomeTags.of(Desolation.id(path));
	}

	private static TagKey<Biome> of(Identifier id) {
		return TagKey.of(RegistryKeys.BIOME, id);
	}
}
