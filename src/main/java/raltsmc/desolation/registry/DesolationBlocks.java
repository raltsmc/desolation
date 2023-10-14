package raltsmc.desolation.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.PressurePlateBlock.ActivationRule;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.block.*;
import raltsmc.desolation.world.feature.DesolationConfiguredFeatures;
import raltsmc.desolation.world.gen.sapling.CharredSaplingGenerator;

public final class DesolationBlocks {

    public static final Block CHARRED_SOIL = register(new Block(FabricBlockSettings.copyOf(Blocks.DIRT).mapColor(MapColor.GRAY).sounds(BlockSoundGroup.GRAVEL)), "charred_soil", true);
    public static final Block COOLED_EMBER_BLOCK = register(new CooledEmberBlock(FabricBlockSettings.copyOf(Blocks.STONE).mapColor(MapColor.DEEPSLATE_GRAY).requiresTool()), "cooled_ember_block", true);
    public static final Block EMBER_BLOCK = register(new EmberBlock(COOLED_EMBER_BLOCK, FabricBlockSettings.copyOf(Blocks.STONE).mapColor(MapColor.ORANGE).luminance(8).requiresTool()), "ember_block", true);
    public static final Block ASH_BLOCK = register(new AshBlock(FabricBlockSettings.copyOf(Blocks.SAND).mapColor(MapColor.GRAY).requiresTool()), "ash_block", true);
    public static final Block ASH_LAYER_BLOCK = register(new AshLayerBlock(FabricBlockSettings.copyOf(Blocks.SAND).mapColor(MapColor.GRAY).strength(0.3f).requiresTool()), "ash", true);
    public static final Block ACTIVATED_CHARCOAL_BLOCK = register(new Block(FabricBlockSettings.copyOf(Blocks.BASALT).mapColor(MapColor.BLACK).strength(0.5f).requiresTool()), "activated_charcoal_block", true);
    public static final Block SCORCHED_TUFT = register(new ScorchedTuftBlock(FabricBlockSettings.copyOf(Blocks.GRASS).mapColor(MapColor.GRAY).sounds(BlockSoundGroup.CROP)), "scorched_tuft", true);
    public static final Block ASH_BRAMBLE = register(new AshBrambleBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).mapColor(MapColor.GRAY).strength(0.3f).sounds(BlockSoundGroup.CROP)), "ash_bramble", true);
    public static final Block CHARRED_BRANCHES = register(new CharredBranchBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).mapColor(MapColor.GRAY).strength(0.3f).sounds(BlockSoundGroup.VINE).allowsSpawning((state, world, pos, entityType) -> false)), "charred_branches", true);
    public static final Block CHARRED_LOG = register(new CharredLogBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).mapColor(MapColor.GRAY).strength(1.8f).sounds(BlockSoundGroup.BASALT)), "charred_log", true);
    public static final Block CHARRED_PLANKS = register(new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).mapColor(MapColor.GRAY)), "charred_planks", true);
    public static final Block CHARRED_SAPLING = register(new CharredSaplingBlock(new CharredSaplingGenerator(() -> DesolationConfiguredFeatures.TREE_CHARRED), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING)), "charred_sapling", true);
    public static final Block POTTED_CHARRED_SAPLING = register(new FlowerPotBlock(CHARRED_SAPLING, FabricBlockSettings.copyOf(Blocks.OAK_SAPLING).mapColor(MapColor.GRAY)), "potted_charred_sapling", false);
    // TODO add signs (and add them to non_flammable_wood, wall_signs, and standing_signs tags) (and add loot table +recipe)
    //public static final Block CHARRED_SIGN = register(new SignBlock(FabricBlockSettings.copyOf(Blocks.OAK_SIGN).mapColor(MapColor.GRAY), WoodType.OAK));
    public static final Block CHARRED_SLAB = register(new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB).mapColor(MapColor.GRAY)), "charred_slab", true);
    public static final Block CHARRED_STAIRS = register(new DesolationStairsBlock(CHARRED_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(CHARRED_PLANKS)), "charred_stairs", true);
    public static final Block CHARRED_PRESSURE_PLATE = register(new DesolationPressurePlateBlock(ActivationRule.EVERYTHING, FabricBlockSettings.copyOf(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.GRAY)), "charred_pressure_plate", true);
    public static final Block CHARRED_TRAPDOOR = register(new DesolationTrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR).mapColor(MapColor.GRAY).strength(3.0f).allowsSpawning((state, world, pos, entityType) -> false)), "charred_trapdoor", true);
    public static final Block CHARRED_FENCE = register(new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE).mapColor(MapColor.GRAY)), "charred_fence", true);
    public static final Block CHARRED_FENCE_GATE = register(new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE_GATE).mapColor(MapColor.GRAY), WoodType.OAK), "charred_fence_gate", true);
    public static final Block CHARRED_BUTTON = register(new DesolationWoodenButtonBlock(FabricBlockSettings.copyOf(Blocks.OAK_BUTTON).mapColor(MapColor.GRAY)), "charred_button", true);
    public static final Block CHARRED_DOOR = register(new DesolationDoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_DOOR).mapColor(MapColor.GRAY)), "charred_door", false);
    public static final Block CINDERFRUIT_PLANT = register(new CinderfruitPlantBlock(FabricBlockSettings.copyOf(Blocks.SWEET_BERRY_BUSH).mapColor(MapColor.TERRACOTTA_GRAY).strength(0.1f).luminance(10).sounds(BlockSoundGroup.CROP)), "cinderfruit_plant", false);

    public static Block register(Block block, String path, Boolean bi) {
        if (bi) {
            Registry.register(Registries.ITEM, Desolation.id(path), new BlockItem(block, new Item.Settings()));
        }
        return Registry.register(Registries.BLOCK, Desolation.id(path), block);
    }

    static void init() { }
}
