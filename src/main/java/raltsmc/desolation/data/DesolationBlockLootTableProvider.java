package raltsmc.desolation.data;

import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import raltsmc.desolation.block.CinderfruitPlantBlock;
import raltsmc.desolation.registry.DesolationBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import raltsmc.desolation.registry.DesolationItems;

public class DesolationBlockLootTableProvider extends FabricBlockLootTableProvider {
	protected DesolationBlockLootTableProvider(FabricDataOutput dataOutput) {
		super(dataOutput);
	}

	@Override
	public void generate() {
		addDrop(DesolationBlocks.ACTIVATED_CHARCOAL_BLOCK);
		addDropWithSilkTouch(DesolationBlocks.ASH_LAYER_BLOCK);
		addDrop(DesolationBlocks.ASH_LAYER_BLOCK, block -> drops(block, DesolationItems.ASH_PILE));
		addDrop(DesolationBlocks.ASH_BLOCK);
		addDrop(DesolationBlocks.CHARRED_BRANCHES, LootTable.builder().pool(LootPool.builder()
				.rolls(ConstantLootNumberProvider.create(2))
				.with(ItemEntry.builder(Items.STICK).conditionally(RandomChanceLootCondition.builder(0.18f)))));
		addDrop(DesolationBlocks.CHARRED_BUTTON);
		addDrop(DesolationBlocks.CHARRED_DOOR, this::doorDrops);
		addDrop(DesolationBlocks.CHARRED_FENCE);
		addDrop(DesolationBlocks.CHARRED_FENCE_GATE);
		addDrop(DesolationBlocks.CHARRED_LOG);
		addDrop(DesolationBlocks.CHARRED_PLANKS);
		addDrop(DesolationBlocks.CHARRED_PRESSURE_PLATE);
		addDrop(DesolationBlocks.CHARRED_SAPLING);
		addDrop(DesolationBlocks.CHARRED_SLAB, this::slabDrops);
		addDrop(DesolationBlocks.CHARRED_SOIL);
		addDrop(DesolationBlocks.CHARRED_STAIRS);
		addDrop(DesolationBlocks.CHARRED_TRAPDOOR);
		addDrop(DesolationBlocks.CINDERFRUIT_PLANT, block -> this.cropDrops(block,
				DesolationItems.CINDERFRUIT, DesolationItems.CINDERFRUIT_SEEDS,
				BlockStatePropertyLootCondition.builder(DesolationBlocks.CINDERFRUIT_PLANT)
						.properties(StatePredicate.Builder.create().exactMatch(CinderfruitPlantBlock.AGE, 1))));
		addDrop(DesolationBlocks.COOLED_EMBER_BLOCK);
		addDrop(DesolationBlocks.EMBER_BLOCK);
		addPottedPlantDrops(DesolationBlocks.POTTED_CHARRED_SAPLING);
	}
}
