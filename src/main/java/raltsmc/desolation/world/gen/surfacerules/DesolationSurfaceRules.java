package raltsmc.desolation.world.gen.surfacerules;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import raltsmc.desolation.registry.DesolationBiomes;
import raltsmc.desolation.registry.DesolationBlocks;

// Contains all of the surface rules used by Desolation
public class DesolationSurfaceRules {

    private static MaterialRules.MaterialRule block(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }

	public static MaterialRules.MaterialRule createRules() {

        // Biome-level rules
        MaterialRules.MaterialRule charredForest = MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH_RANGE_6,
                MaterialRules.condition(MaterialRules.biome(DesolationBiomes.CHARRED_FOREST,
                        DesolationBiomes.CHARRED_FOREST_CLEARING,
                        DesolationBiomes.CHARRED_FOREST_SMALL),
            MaterialRules.sequence(MaterialRules.condition(MaterialRules.water(0, 0),
                    block(DesolationBlocks.CHARRED_SOIL)),
                    block(Blocks.DIRT))));

        // At the moment, there's just Charred Forest (and variants).  To add another, wrap them in MaterialRules.sequence()
        return MaterialRules.condition(MaterialRules.surface(),
                charredForest);
	}

    public static void init() { }
}
