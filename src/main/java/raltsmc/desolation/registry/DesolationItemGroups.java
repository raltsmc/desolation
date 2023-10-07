package raltsmc.desolation.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import raltsmc.desolation.Desolation;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

public class DesolationItemGroups {
    private static final HashMap<RegistryKey<ItemGroup>, HashMap<ItemConvertible, ItemGroupEntries>> ITEM_GROUP_ENTRY_MAPS;
    private static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(Desolation.MOD_ID, "items"));

    /*
     * These items are the last Vanilla item of a "similar" type to items we add to Vanilla groups.
     * Each is used to build a collection of items which will be inserted below the Vanilla item.
     */
    private static final Item BUILDING_WOOD_ITEMS = Items.MANGROVE_BUTTON;
    private static final Item FOOD_BERRIES = Items.GLOW_BERRIES;
    private static final Item FUNCTIONAL_SIGN = Items.CHERRY_HANGING_SIGN;
    private static final Item INGREDIENTS_COAL = Items.CHARCOAL;
    private static final Item NATURAL_DIRT_ITEMS = Items.FARMLAND;
    private static final Item NATURAL_GRASS = Items.GRASS;
    private static final Item NATURAL_HOT_BLOCKS = Items.MAGMA_BLOCK;
    private static final Item NATURAL_LEAVES = Items.MANGROVE_LEAVES;
    private static final Item NATURAL_LOG = Items.MANGROVE_LOG;
    private static final Item NATURAL_SAPLING = Items.MANGROVE_PROPAGULE;
    private static final Item NATURAL_SEEDS = Items.BEETROOT_SEEDS;
    private static final Item NATURAL_SHRUBS = Items.DEAD_BUSH;
    private static final Item NATURAL_SNOWLIKE = Items.MOSS_CARPET;
    private static final Item TOOLS_BOAT = Items.MANGROVE_CHEST_BOAT;
    private static final Item TOOLS_DISC = Items.MUSIC_DISC_PIGSTEP;
    private static final Item TOOLS_WEARABLE = Items.ELYTRA;

    static {
        ITEM_GROUP_ENTRY_MAPS = new HashMap<>(8);

        /*
         * For each Vanilla item group, add the same kinds of items Vanilla adds.
         * Since Minecraft 1.19.3, items are often in multiple item groups...
         */

        // BUILDING BLOCKS

        // Wood items
        addGroupEntry(DesolationBlocks.CHARRED_LOG, ItemGroups.BUILDING_BLOCKS, BUILDING_WOOD_ITEMS);
        //addGroupEntry(DesolationBlocks.CHARRED_WOOD, ItemGroups.BUILDING_BLOCKS, BUILDING_WOOD_ITEMS);
        //addGroupEntry(DesolationBlocks.STRIPPED_CHARRED_LOG, ItemGroups.BUILDING_BLOCKS, BUILDING_WOOD_ITEMS);
        //addGroupEntry(DesolationBlocks.STRIPPED_CHARRED_WOOD, ItemGroups.BUILDING_BLOCKS, BUILDING_WOOD_ITEMS);
        addGroupEntry(DesolationBlocks.CHARRED_PLANKS, ItemGroups.BUILDING_BLOCKS, BUILDING_WOOD_ITEMS);
        addGroupEntry(DesolationBlocks.CHARRED_STAIRS, ItemGroups.BUILDING_BLOCKS, BUILDING_WOOD_ITEMS);
        addGroupEntry(DesolationBlocks.CHARRED_SLAB, ItemGroups.BUILDING_BLOCKS, BUILDING_WOOD_ITEMS);
        addGroupEntry(DesolationBlocks.CHARRED_FENCE, ItemGroups.BUILDING_BLOCKS, BUILDING_WOOD_ITEMS);
        addGroupEntry(DesolationBlocks.CHARRED_FENCE_GATE, ItemGroups.BUILDING_BLOCKS, BUILDING_WOOD_ITEMS);
        addGroupEntry(DesolationItems.CHARRED_DOOR, ItemGroups.BUILDING_BLOCKS, BUILDING_WOOD_ITEMS);
        addGroupEntry(DesolationBlocks.CHARRED_TRAPDOOR, ItemGroups.BUILDING_BLOCKS, BUILDING_WOOD_ITEMS);
        addGroupEntry(DesolationBlocks.CHARRED_PRESSURE_PLATE, ItemGroups.BUILDING_BLOCKS, BUILDING_WOOD_ITEMS);
        addGroupEntry(DesolationBlocks.CHARRED_BUTTON, ItemGroups.BUILDING_BLOCKS, BUILDING_WOOD_ITEMS);

        // Misc.
        addGroupEntry(DesolationBlocks.ACTIVATED_CHARCOAL_BLOCK, ItemGroups.BUILDING_BLOCKS, Items.COAL_BLOCK);


        // NATURAL

        // Dirt Items
        addGroupEntry(DesolationBlocks.CHARRED_SOIL, ItemGroups.NATURAL, NATURAL_DIRT_ITEMS);

        // Hot blocks
        addGroupEntry(DesolationBlocks.EMBER_BLOCK, ItemGroups.NATURAL, NATURAL_HOT_BLOCKS);
        addGroupEntry(DesolationBlocks.COOLED_EMBER_BLOCK, ItemGroups.NATURAL, NATURAL_HOT_BLOCKS);

        // Snow-like
        addGroupEntry(DesolationBlocks.ASH_BLOCK, ItemGroups.NATURAL, NATURAL_SNOWLIKE);
        addGroupEntry(DesolationBlocks.ASH_LAYER_BLOCK, ItemGroups.NATURAL, NATURAL_SNOWLIKE);

        // Wood
        addGroupEntry(DesolationBlocks.CHARRED_LOG, ItemGroups.NATURAL, NATURAL_LOG);

        // Saplings
        addGroupEntry(DesolationBlocks.CHARRED_SAPLING, ItemGroups.NATURAL, NATURAL_SAPLING);

        // Leaves
        addGroupEntry(DesolationBlocks.CHARRED_BRANCHES, ItemGroups.NATURAL, NATURAL_LEAVES);

        // Seeds
        addGroupEntry(DesolationItems.CINDERFRUIT_SEEDS, ItemGroups.NATURAL, NATURAL_SEEDS);

        // Grass
        addGroupEntry(DesolationBlocks.SCORCHED_TUFT, ItemGroups.NATURAL, NATURAL_GRASS);

        // Berries
        addGroupEntry(DesolationBlocks.ASH_BRAMBLE, ItemGroups.NATURAL, NATURAL_SHRUBS);


        // FUNCTIONAL

        // Wood Items
        //addGroupEntry(DesolationItems.CHARRED_SIGN, ItemGroups.FUNCTIONAL, FUNCTIONAL_SIGN);
        //addGroupEntry(DesolationItems.CHARRED_HANGING_SIGN, ItemGroups.FUNCTIONAL, FUNCTIONAL_SIGN);


        // REDSTONE


        // HOTBAR


        // SEARCH


        // TOOLS

        // Wearable
        addGroupEntry(DesolationItems.MASK, ItemGroups.TOOLS, TOOLS_WEARABLE);
        addGroupEntry(DesolationItems.GOGGLES, ItemGroups.TOOLS, TOOLS_WEARABLE);
        addGroupEntry(DesolationItems.AIR_FILTER, ItemGroups.TOOLS, TOOLS_WEARABLE);

        // Boats
        //addGroupEntry(DesolationBoatTypes.CHARRED_BOAT, ItemGroups.TOOLS, TOOLS_BOAT);
        //addGroupEntry(DesolationBoatTypes.CHARRED_CHEST_BOAT, ItemGroups.TOOLS, TOOLS_BOAT);

        // Discs
        addGroupEntry(DesolationItems.MUSIC_DISC_ASHES, ItemGroups.TOOLS, TOOLS_DISC);


        // COMBAT


        // FOOD AND DRINK

        // Berries
        addGroupEntry(DesolationItems.CINDERFRUIT, ItemGroups.FOOD_AND_DRINK, FOOD_BERRIES);


        // INGREDIENTS

        // Charcoal and Ash
        addGroupEntry(DesolationItems.ACTIVATED_CHARCOAL, ItemGroups.INGREDIENTS, INGREDIENTS_COAL);
        addGroupEntry(DesolationItems.CHARCOAL_BIT, ItemGroups.INGREDIENTS, INGREDIENTS_COAL);
        addGroupEntry(DesolationItems.ASH_PILE, ItemGroups.INGREDIENTS, INGREDIENTS_COAL);
        addGroupEntry(DesolationItems.PRIMED_ASH, ItemGroups.INGREDIENTS, INGREDIENTS_COAL);

        // Misc.
        addGroupEntry(DesolationItems.INFUSED_POWDER, ItemGroups.INGREDIENTS, Items.GUNPOWDER);
        addGroupEntry(DesolationItems.HEART_OF_CINDER, ItemGroups.INGREDIENTS, Items.HEART_OF_THE_SEA);


        // SPAWN EGGS
        addGroupEntry(DesolationItems.SPAWN_EGG_ASH_SCUTTLER, ItemGroups.SPAWN_EGGS, Items.ALLAY_SPAWN_EGG);
        addGroupEntry(DesolationItems.SPAWN_EGG_BLACKENED, ItemGroups.SPAWN_EGGS, Items.BEE_SPAWN_EGG);


        // INVENTORY


        /*
         * Add the items configured above to the Vanilla item groups.
         */
        for (RegistryKey<ItemGroup> group : ITEM_GROUP_ENTRY_MAPS.keySet()) {
            ItemGroupEvents.modifyEntriesEvent(group).register((content) -> {
                FeatureSet featureSet = content.getEnabledFeatures();
                HashMap<ItemConvertible, ItemGroupEntries> entryMap = ITEM_GROUP_ENTRY_MAPS.get(group);

                for (ItemConvertible relative : entryMap.keySet()) {
                    ItemGroupEntries entries = entryMap.get(relative);

                    // FAPI does not give us a way to add at a feature-flag-disabled location.
                    // So, below we have to adjust for any items which may be disabled.
                    if (relative == null) {
                        // Target the end of the Item Group
                        content.addAll(entries.getCollection());
                    } else if (relative.equals(Items.MANGROVE_HANGING_SIGN) && !Items.MANGROVE_HANGING_SIGN.isEnabled(featureSet)) {
                        content.addAfter(Items.MANGROVE_SIGN, entries.getCollection());
                    } else {
                        //Desolation.LOGGER.warn("About to add to Vanilla Item Group '{}' after Item '{}': '{}'", group.getId(), relative, entries.getCollection().stream().map(ItemStack::getItem).collect(Collectors.toList()));
                        content.addAfter(relative, entries.getCollection());
                    }
                }
            });
        }


        /*
         * Also add all the items to Desolation's own item group.
         */
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder()
                .displayName(Text.literal("Desolation"))
                .icon(() -> DesolationBlocks.EMBER_BLOCK.asItem().getDefaultStack())
                .entries((context, entries) -> {
                    ITEM_GROUP_ENTRY_MAPS.values().stream()
                            .map(HashMap::values).flatMap(Collection::stream)
                            .map(ItemGroupEntries::getCollection).flatMap(Collection::stream)
                            .collect(Collectors.groupingByConcurrent(ItemStack::getItem)).keySet().stream()
                            .sorted(Comparator.comparing((item) -> item.getName().getString())).forEach(entries::add);
                }).build()
        );
    }

    public static void addGroupEntry(ItemConvertible item, RegistryKey<ItemGroup> group) {
        // Appends the item to the bottom of the group.
        addGroupEntry(item, group, null);
    }

    public static void addGroupEntry(ItemConvertible item, RegistryKey<ItemGroup> group, @Nullable ItemConvertible relative) {
        HashMap<ItemConvertible, ItemGroupEntries> entryMap = ITEM_GROUP_ENTRY_MAPS.computeIfAbsent(group, (key) -> new HashMap<>(32));
        ItemGroupEntries entries = entryMap.computeIfAbsent(relative, ItemGroupEntries::empty);
        entries.addItem(item);
    }

    public static void init() { }
}
