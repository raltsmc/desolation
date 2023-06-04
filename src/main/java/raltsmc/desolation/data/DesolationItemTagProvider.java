package raltsmc.desolation.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.registry.DesolationItems;
import raltsmc.desolation.tag.DesolationItemTags;

import java.util.concurrent.CompletableFuture;

public class DesolationItemTagProvider extends FabricTagProvider.ItemTagProvider {
	protected DesolationItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture, BlockTagProvider blockTagProvider) {
		super(output, registriesFuture, blockTagProvider);
	}

	@Override
	public void configure(RegistryWrapper.WrapperLookup registries) {
//		getOrCreateTagBuilder(ItemTags.BOATS)
//			.add(DesolationBoatTypes.CHARRED_BOAT);

//		getOrCreateTagBuilder(ItemTags.CHEST_BOATS)
//			.add(DesolationBoatTypes.CHARRED_CHEST_BOAT);

		copy(BlockTags.LEAVES, ItemTags.LEAVES);

		copy(BlockTags.LOGS, ItemTags.LOGS);

		getOrCreateTagBuilder(ItemTags.MUSIC_DISCS)
			.add(DesolationItems.MUSIC_DISC_ASHES);

		getOrCreateTagBuilder(ItemTags.NON_FLAMMABLE_WOOD)
			.addTag(DesolationItemTags.CHARRED_LOGS)
			.add(DesolationBlocks.CHARRED_BUTTON.asItem())
			.add(DesolationBlocks.CHARRED_DOOR.asItem())
			.add(DesolationBlocks.CHARRED_FENCE.asItem())
			.add(DesolationBlocks.CHARRED_FENCE_GATE.asItem())
			.add(DesolationBlocks.CHARRED_PLANKS.asItem())
			.add(DesolationBlocks.CHARRED_PRESSURE_PLATE.asItem())
			.add(DesolationBlocks.CHARRED_SLAB.asItem())
			.add(DesolationBlocks.CHARRED_STAIRS.asItem())
			.add(DesolationBlocks.CHARRED_TRAPDOOR.asItem());

		copy(BlockTags.PLANKS, ItemTags.PLANKS);

		copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);

//		copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);

//		copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);

		copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);

		copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);

		copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);

		copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);

		copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);

		copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);

		copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);


		getOrCreateTagBuilder(DesolationItemTags.CHARRED_LOGS)
			.add(DesolationBlocks.CHARRED_LOG.asItem());
//			.add(DesolationBlocks.CHARRED_WOOD.asItem())
//			.add(DesolationBlocks.STRIPPED_CHARRED_LOG.asItem())
//			.add(DesolationBlocks.STRIPPED_CHARRED_WOOD.asItem());
	}
}
