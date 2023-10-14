package raltsmc.desolation.tag;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import raltsmc.desolation.Desolation;

public final class DesolationBlockTags {
	public static final TagKey<Block> CHARRED_LOGS = DesolationBlockTags.of("charred_logs");
	public static final TagKey<Block> SCORCHED_EARTH = DesolationBlockTags.of("scorched_earth");

	private DesolationBlockTags() {
		return;
	}

	private static TagKey<Block> of(String path) {
		return DesolationBlockTags.of(Desolation.id(path));
	}

	private static TagKey<Block> of(Identifier id) {
		return TagKey.of(RegistryKeys.BLOCK, id);
	}
}
