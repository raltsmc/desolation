package raltsmc.desolation.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.DesolationMod;
import raltsmc.desolation.block.*;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

public final class DesolationBlocks {

    public static final Block EMBER_BLOCK =
            register(new EmberBlock(FabricBlockSettings.of(Material.STONE).hardness(1.5f).lightLevel(8).sounds(BlockSoundGroup.STONE)),
                    "ember_block");
    public static final Block ASH_BLOCK =
            register(new AshBlock(FabricBlockSettings.of(Material.AGGREGATE).hardness(0.5f).sounds(BlockSoundGroup.SAND)),
            "ash_block");
    public static final Block ASH_LAYER_BLOCK =
            register(new AshLayerBlock(FabricBlockSettings.of(Material.AGGREGATE).hardness(0.3f).sounds(BlockSoundGroup.SAND)), "ash");
    public static final Block CHARRED_LOG =
            register(new PillarBlock(FabricBlockSettings.of(Material.WOOD).hardness(1.8f).sounds(BlockSoundGroup.BASALT)),
        "charred_log");
    public static final Block CHARRED_BRANCHES =
            register(new CharredBranchBlock(FabricBlockSettings.of(Material.LEAVES).hardness(0.3f).nonOpaque().sounds(BlockSoundGroup.VINE)), "charred_branches");
    public static final Block ASH_BRAMBLE =
            register(new Block(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).hardness(0.3f).noCollision().nonOpaque().sounds(BlockSoundGroup.VINE)), "ash_bramble");
    public static final Block CHARRED_SOIL =
            register(new Block(FabricBlockSettings.of(Material.SOIL).hardness(0.5f).sounds(BlockSoundGroup.GRAVEL))
            , "charred_soil");
    public static final Block SCORCHED_TUFT =
            register(new ScorchedTuftBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).breakInstantly().nonOpaque().noCollision().sounds(BlockSoundGroup.CROP)),
                    "scorched_tuft");
    public static final Block CINDERFRUIT_PLANT =
            register(new CinderfruitPlantBlock(FabricBlockSettings.of(Material.PLANT).hardness(0.1f).lightLevel(10)
                    .nonOpaque().noCollision().sounds(BlockSoundGroup.CROP)), "cinderfruit_plant");

    static void init() {

    }

    public static Block register(Block block, String path) {
        Registry.register(Registry.ITEM, Desolation.id(path), new BlockItem(block,
                new Item.Settings().group(DesolationMod.DSL_GROUP)));
        System.out.println("Registering block: " + path);
        return Registry.register(Registry.BLOCK, Desolation.id(path), block);
    }
}
