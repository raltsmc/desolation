package raltsmc.desolation.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.registry.DesolationItems;
import raltsmc.desolation.tag.DesolationItemTags;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class DesolationRecipeProvider extends FabricRecipeProvider {
	protected DesolationRecipeProvider(FabricDataOutput dataOutput) {
		super(dataOutput);
	}

	@Override
	public void generate(Consumer<RecipeJsonProvider> exporter) {
		// vanilla recipes
		offer2x2CompactingRecipe(exporter, RecipeCategory.MISC, Items.CHARCOAL, DesolationItems.CHARCOAL_BIT);

		ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.GUNPOWDER, 1)
				.input(Items.BONE_MEAL)
				.input(Items.CHARCOAL)
				.input(Items.CLAY_BALL)
				.criterion("has_sulfurs", InventoryChangedCriterion.Conditions.items(
						new ItemPredicate(null, Set.of(Items.CHARCOAL), NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, EnchantmentPredicate.ARRAY_OF_ANY, EnchantmentPredicate.ARRAY_OF_ANY, null, NbtPredicate.ANY)))
				.offerTo(exporter);


		// misc. recipes
		offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, DesolationItems.ACTIVATED_CHARCOAL, RecipeCategory.BUILDING_BLOCKS, DesolationBlocks.ACTIVATED_CHARCOAL_BLOCK);

		offerSmelting(exporter, List.of(Items.CHARCOAL), RecipeCategory.MISC, DesolationItems.ACTIVATED_CHARCOAL, 3.0f, 800, "charcoal");

		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, DesolationItems.AIR_FILTER, 1)
				.pattern("CCC")
				.pattern("CPC")
				.pattern("CCC")
				.input('C', DesolationItems.ACTIVATED_CHARCOAL)
				.input('P', Items.PAPER)
				.criterion("has_activated_charcoal", InventoryChangedCriterion.Conditions.items(DesolationItems.ACTIVATED_CHARCOAL))
				.offerTo(exporter);

		offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, DesolationItems.ASH_PILE, RecipeCategory.BUILDING_BLOCKS, DesolationBlocks.ASH_BLOCK);

		offerShapelessRecipe(exporter, DesolationItems.CHARCOAL_BIT, Items.CHARCOAL, "charcoal", 4);

		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, DesolationItems.GOGGLES, 1)
				.pattern("GSG")
				.pattern("LSL")
				.pattern("GSG")
				.input('G', Items.GOLD_INGOT)
				.input('L', Items.LEATHER)
				.input('S', Items.GLASS_PANE)
				.criterion("has_gold_ingot", InventoryChangedCriterion.Conditions.items(Items.GOLD_INGOT))
				.offerTo(exporter);

		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, DesolationItems.INFUSED_POWDER, 1)
				.pattern("CCC")
				.pattern("CFC")
				.pattern("CCC")
				.input('C', DesolationItems.ACTIVATED_CHARCOAL)
				.input('F', DesolationItems.CINDERFRUIT)
				.criterion("has_cinderfruit", InventoryChangedCriterion.Conditions.items(DesolationItems.CINDERFRUIT))
				.offerTo(exporter);

		ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, DesolationItems.MASK, 1)
				.pattern("LLL")
				.pattern("SFS")
				.pattern("LLL")
				.input('F', DesolationItems.AIR_FILTER)
				.input('L', Items.LEATHER)
				.input('S', Items.STRING)
				.criterion("has_air_filter", InventoryChangedCriterion.Conditions.items(DesolationItems.AIR_FILTER))
				.offerTo(exporter);

		//ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, DesolationItems.MASK_GOGGLES, 1)
		//		.input(DesolationItems.GOGGLES)
		//		.input(DesolationItems.MASK)
		//		.criterion("has_mask_and_goggles", InventoryChangedCriterion.Conditions.items(
		//				new ItemPredicate(null, Set.of(DesolationItems.GOGGLES, DesolationItems.MASK), NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, EnchantmentPredicate.ARRAY_OF_ANY, EnchantmentPredicate.ARRAY_OF_ANY, null, NbtPredicate.ANY)))
		//		.offerTo(exporter);

		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, DesolationItems.PRIMED_ASH, 1)
				.pattern("CCC")
				.pattern("CAC")
				.pattern("CCC")
				.input('A', DesolationItems.ASH_PILE)
				.input('C', DesolationItems.ACTIVATED_CHARCOAL)
				.criterion("has_ash_pile", InventoryChangedCriterion.Conditions.items(DesolationItems.ASH_PILE))
				.offerTo(exporter);


		// wood recipes
//		offerBoatRecipe(exporter, DesolationBoatTypes.CHARRED_BOAT, DesolationBlocks.CHARRED_PLANKS);

//		offerChestBoatRecipe(exporter, DesolationBoatTypes.CHARRED_CHEST_BOAT, DesolationBoatTypes.CHARRED_BOAT);

		offerSingleOutputShapelessRecipe(exporter, DesolationBlocks.CHARRED_BUTTON, DesolationBlocks.CHARRED_PLANKS, "redstone");

		createDoorRecipe(DesolationBlocks.CHARRED_DOOR, Ingredient.ofItems(DesolationBlocks.CHARRED_PLANKS))
				.criterion("has_planks", InventoryChangedCriterion.Conditions.items(DesolationBlocks.CHARRED_PLANKS))
				.offerTo(exporter);

		createFenceRecipe(DesolationBlocks.CHARRED_FENCE, Ingredient.ofItems(DesolationBlocks.CHARRED_PLANKS))
				.criterion("has_planks", InventoryChangedCriterion.Conditions.items(DesolationBlocks.CHARRED_PLANKS))
				.offerTo(exporter);

		createFenceGateRecipe(DesolationBlocks.CHARRED_FENCE_GATE, Ingredient.ofItems(DesolationBlocks.CHARRED_PLANKS))
				.criterion("has_planks", InventoryChangedCriterion.Conditions.items(DesolationBlocks.CHARRED_PLANKS))
				.offerTo(exporter);

//		offerHangingSignRecipe(exporter, DesolationBlocks.CHARRED_HANGING_SIGN, DesolationBlocks.STRIPPED_CHARRED_LOG);

		offerPlanksRecipe(exporter, DesolationBlocks.CHARRED_PLANKS, DesolationItemTags.CHARRED_LOGS, 4);

		offerPressurePlateRecipe(exporter, DesolationBlocks.CHARRED_PRESSURE_PLATE, DesolationBlocks.CHARRED_PLANKS);

//		createSignRecipe(DesolationBlocks.CHARRED_SIGN, Ingredient.ofItems(DesolationBlocks.CHARRED_PLANKS))
//				.criterion("has_planks", InventoryChangedCriterion.Conditions.items(DesolationBlocks.CHARRED_PLANKS))
//				.offerTo(exporter);

		offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, DesolationBlocks.CHARRED_SLAB, DesolationBlocks.CHARRED_PLANKS);

		createStairsRecipe(DesolationBlocks.CHARRED_STAIRS, Ingredient.ofItems(DesolationBlocks.CHARRED_PLANKS))
				.criterion("has_planks", InventoryChangedCriterion.Conditions.items(DesolationBlocks.CHARRED_PLANKS))
				.offerTo(exporter);

		createTrapdoorRecipe(DesolationBlocks.CHARRED_TRAPDOOR, Ingredient.ofItems(DesolationBlocks.CHARRED_PLANKS))
				.criterion("has_planks", InventoryChangedCriterion.Conditions.items(DesolationBlocks.CHARRED_PLANKS))
				.offerTo(exporter);

//		new ShapedRecipeJsonBuilder(RecipeCategory.BUILDING_BLOCKS, DesolationBlocks.CHARRED_WOOD, 3)
//				.pattern("LL")
//				.pattern("LL")
//				.input('L', DesolationBlocks.CHARRED_LOG)
//				.criterion("has_logs", InventoryChangedCriterion.Conditions.items(DesolationBlocks.CHARRED_LOG))
//				.offerTo(exporter, Desolation.id("charred_wood"));

//		new ShapedRecipeJsonBuilder(RecipeCategory.BUILDING_BLOCKS, DesolationBlocks.STRIPPED_CHARRED_WOOD, 3)
//				.pattern("LL")
//				.pattern("LL")
//				.input('L', DesolationBlocks.STRIPPED_CHARRED_LOG)
//				.criterion("has_logs", InventoryChangedCriterion.Conditions.items(DesolationBlocks.STRIPPED_CHARRED_LOG))
//				.offerTo(exporter, Desolation.id("stripped_charred_wood"));
	}

	@Override
	protected Identifier getRecipeIdentifier(Identifier identifier) {
		return Desolation.id(identifier.getPath());
	}
}
