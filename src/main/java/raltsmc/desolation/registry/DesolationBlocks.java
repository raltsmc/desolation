package raltsmc.desolation.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.*;
import net.minecraft.block.PressurePlateBlock.ActivationRule;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.DesolationMod;
import raltsmc.desolation.block.*;

public final class DesolationBlocks {

    public static final Block CHARRED_SOIL = register(new Block(FabricBlockSettings.of(Material.SOIL).hardness(0.5f).sounds(BlockSoundGroup.GRAVEL).breakByTool(FabricToolTags.SHOVELS)), "charred_soil", true);
    public static final Block COOLED_EMBER_BLOCK = register(new CooledEmberBlock(FabricBlockSettings.of(Material.STONE).hardness(1.5f).sounds(BlockSoundGroup.STONE).breakByTool(FabricToolTags.PICKAXES, 0)), "cooled_ember_block", true);
    public static final Block EMBER_BLOCK = register(new EmberBlock(COOLED_EMBER_BLOCK, FabricBlockSettings.of(Material.STONE).hardness(1.5f).lightLevel(8).sounds(BlockSoundGroup.STONE).breakByTool(FabricToolTags.PICKAXES, 0)), "ember_block", true);
    public static final Block ASH_BLOCK = register(new AshBlock(FabricBlockSettings.of(Material.AGGREGATE).hardness(0.5f).sounds(BlockSoundGroup.SAND).breakByTool(FabricToolTags.SHOVELS)), "ash_block", true);
    public static final Block ASH_LAYER_BLOCK = register(new AshLayerBlock(FabricBlockSettings.of(Material.AGGREGATE).hardness(0.3f).sounds(BlockSoundGroup.SAND).breakByTool(FabricToolTags.SHOVELS)), "ash", true);
    public static final Block ACTIVATED_CHARCOAL_BLOCK = register(new Block(FabricBlockSettings.of(Material.STONE).hardness(0.5f).sounds(BlockSoundGroup.BASALT).breakByTool(FabricToolTags.PICKAXES)), "activated_charcoal_block", true);
    public static final Block SCORCHED_TUFT = register(new ScorchedTuftBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).breakInstantly().nonOpaque().noCollision().sounds(BlockSoundGroup.CROP)), "scorched_tuft", true);
    public static final Block ASH_BRAMBLE = register(new AshBrambleBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).hardness(0.3f).noCollision().nonOpaque().sounds(BlockSoundGroup.CROP).breakByTool(FabricToolTags.SWORDS)), "ash_bramble", true);
    // TODO make branches decay properly (extend LeavesBlock without the ugly rendering)
    public static final Block CHARRED_BRANCHES = register(new Block(FabricBlockSettings.of(Material.LEAVES).hardness(0.3f).nonOpaque().sounds(BlockSoundGroup.VINE).breakByTool(FabricToolTags.SWORDS).allowsSpawning((state, world, pos, entityType) -> { return false; })), "charred_branches", true);
    public static final Block CHARRED_LOG = register(new PillarBlock(FabricBlockSettings.of(Material.WOOD).hardness(1.8f).sounds(BlockSoundGroup.BASALT).breakByTool(FabricToolTags.AXES)), "charred_log", true);
    public static final Block CHARRED_PLANKS = register(new Block(FabricBlockSettings.of(Material.WOOD, MaterialColor.GRAY).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), "charred_planks", true);
    // TODO add signs (and add them to non_flammable_wood, wall_signs, and standing_signs tags) (and add loot table +recipe)
    //public static final Block CHARRED_SIGN = register(new SignBlock(FabricBlockSettings.of(Material.WOOD,MaterialColor.GRAY).noCollision().strength(1.0f).sounds(BlockSoundGroup.WOOD)));
    public static final Block CHARRED_SLAB = register(new SlabBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.GRAY).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), "charred_slab", true);
    public static final Block CHARRED_STAIRS = register(new DesolationStairsBlock(CHARRED_PLANKS.getDefaultState(), Settings.copy(CHARRED_PLANKS)), "charred_stairs", true);
    public static final Block CHARRED_PRESSURE_PLATE = register(new DesolationPressurePlateBlock(ActivationRule.EVERYTHING, FabricBlockSettings.of(Material.WOOD, MaterialColor.GRAY).noCollision().strength(0.5f).sounds(BlockSoundGroup.WOOD)), "charred_pressure_plate", true);
    public static final Block CHARRED_TRAPDOOR = register(new DesolationTrapdoorBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.GRAY).strength(3.0f).sounds(BlockSoundGroup.WOOD).nonOpaque().allowsSpawning((state, world, pos, entityType) -> { return false; })), "charred_trapdoor", true);
    public static final Block CHARRED_FENCE = register(new FenceBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.GRAY).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), "charred_fence", true);
    public static final Block CHARRED_FENCE_GATE = register(new FenceGateBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.GRAY).strength(2.0f, 3.0f).sounds(BlockSoundGroup.WOOD)), "charred_fence_gate", true);
    public static final Block CHARRED_BUTTON = register(new DesolationWoodenButtonBlock(FabricBlockSettings.of(Material.SUPPORTED).noCollision().strength(0.5f).sounds(BlockSoundGroup.WOOD)), "charred_button", true);
    public static final Block CHARRED_DOOR = register(new DesolationDoorBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.GRAY).strength(3.0f).sounds(BlockSoundGroup.WOOD).nonOpaque()), "charred_door", false);
    public static final Block CINDERFRUIT_PLANT = register(new CinderfruitPlantBlock(FabricBlockSettings.of(Material.PLANT).hardness(0.1f).lightLevel(10).nonOpaque().noCollision().sounds(BlockSoundGroup.CROP)), "cinderfruit_plant", false);

    static void init() {

    }

    public static Block register(Block block, String path, Boolean bi) {
        if (bi) {
            Registry.register(Registry.ITEM, Desolation.id(path), new BlockItem(block, new Item.Settings().group(DesolationMod.DSL_GROUP)));
        }
        return Registry.register(Registry.BLOCK, Desolation.id(path), block);
    }
}
