package raltsmc.desolation.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.tag.DesolationBlockTags;

import java.util.concurrent.CompletableFuture;

public class DesolationBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	protected DesolationBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void configure(RegistryWrapper.WrapperLookup registries) {
		getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
			.add(DesolationBlocks.CHARRED_FENCE_GATE);

		getOrCreateTagBuilder(BlockTags.HOE_MINEABLE)
			.add(DesolationBlocks.CHARRED_BRANCHES);

		getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
			.add(DesolationBlocks.ACTIVATED_CHARCOAL_BLOCK)
			.add(DesolationBlocks.COOLED_EMBER_BLOCK)
			.add(DesolationBlocks.EMBER_BLOCK);

		getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
			.add(DesolationBlocks.ASH_BLOCK)
			.add(DesolationBlocks.ASH_LAYER_BLOCK)
			.add(DesolationBlocks.CHARRED_SOIL);

//		getOrCreateTagBuilder(BlockTags.SWORD_MINEABLE)
//			.add(DesolationBlocks.ASH_BRAMBLE);


		getOrCreateTagBuilder(BlockTags.FLOWER_POTS)
			.add(DesolationBlocks.POTTED_CHARRED_SAPLING);

		getOrCreateTagBuilder(BlockTags.LEAVES)
			.add(DesolationBlocks.CHARRED_BRANCHES);

		getOrCreateTagBuilder(BlockTags.INFINIBURN_OVERWORLD)
			.add(DesolationBlocks.EMBER_BLOCK);

		getOrCreateTagBuilder(BlockTags.LOGS)
			.addTag(DesolationBlockTags.CHARRED_LOGS);

		getOrCreateTagBuilder(BlockTags.PLANKS)
			.add(DesolationBlocks.CHARRED_PLANKS);

		getOrCreateTagBuilder(BlockTags.SAPLINGS)
			.add(DesolationBlocks.CHARRED_SAPLING);

//		getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS)
//			.add(DesolationBlocks.CHARRED_HANGING_SIGN);

//		getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS)
//			.add(DesolationBlocks.CHARRED_WALL_HANGING_SIGN);

//		getOrCreateTagBuilder(BlockTags.STANDING_SIGNS)
//			.add(DesolationBlocks.CHARRED_SIGN);

//		getOrCreateTagBuilder(BlockTags.WALL_SIGNS)
//			.add(DesolationBlocks.CHARRED_WALL_SIGN);

		getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS)
			.add(DesolationBlocks.CHARRED_BUTTON);

		getOrCreateTagBuilder(BlockTags.WOODEN_DOORS)
			.add(DesolationBlocks.CHARRED_DOOR);

		getOrCreateTagBuilder(BlockTags.WOODEN_FENCES)
			.add(DesolationBlocks.CHARRED_FENCE);

		getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES)
			.add(DesolationBlocks.CHARRED_PRESSURE_PLATE);

		getOrCreateTagBuilder(BlockTags.WOODEN_SLABS)
			.add(DesolationBlocks.CHARRED_SLAB);

		getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS)
			.add(DesolationBlocks.CHARRED_STAIRS);

		getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS)
			.add(DesolationBlocks.CHARRED_TRAPDOOR);


		getOrCreateTagBuilder(DesolationBlockTags.CHARRED_LOGS)
			.add(DesolationBlocks.CHARRED_LOG);
//			.add(DesolationBlocks.CHARRED_WOOD)
//			.add(DesolationBlocks.STRIPPED_CHARRED_LOG)
//			.add(DesolationBlocks.STRIPPED_CHARRED_WOOD);

		getOrCreateTagBuilder(DesolationBlockTags.SCORCHED_EARTH)
			.add(DesolationBlocks.ASH_BLOCK)
			.add(DesolationBlocks.CHARRED_SOIL)
			.add(DesolationBlocks.COOLED_EMBER_BLOCK)
			.add(DesolationBlocks.EMBER_BLOCK);
	}
}
