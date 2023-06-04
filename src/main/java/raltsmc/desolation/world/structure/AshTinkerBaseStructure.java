package raltsmc.desolation.world.structure;

import com.mojang.serialization.Codec;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.registry.DesolationStructures;

import java.util.Optional;

public class AshTinkerBaseStructure extends Structure {
    public static final Codec<AshTinkerBaseStructure> CODEC = AshTinkerBaseStructure.createCodec(AshTinkerBaseStructure::new);
    private static final Identifier TINKER_BASE_MAIN = Desolation.id("ash_tinker_base/ash_tinker_base");

    public AshTinkerBaseStructure(Structure.Config config) {
        super(config);
    }

    @Override
    public Optional<StructurePosition> getStructurePosition(Structure.Context context) {
        if (Structure.getMinCornerHeight(context, 16, 16) > context.chunkGenerator().getSeaLevel()) {
            return getStructurePosition(context, Heightmap.Type.WORLD_SURFACE_WG, collector -> this.addPieces(collector, context));
        } else {
            return Optional.empty();
        }
    }

    private void addPieces(StructurePiecesCollector collector, Structure.Context context) {
        int x = context.chunkPos().getCenterX();
        int z = context.chunkPos().getCenterZ();
        int y = context.chunkGenerator().getHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG, context.world(), context.noiseConfig());
        BlockPos pos = new BlockPos(x, y, z);
        BlockRotation rotation = BlockRotation.random(context.random());
        StructureTemplateManager manager = context.structureTemplateManager();

        collector.addPiece(new AshTinkerBaseGenerator(manager, TINKER_BASE_MAIN, pos, rotation));

        collector.getBoundingBox();
    }

    @Override
    public StructureType<?> getType() {
        return DesolationStructures.ASH_TINKER_BASE_STRUCTURE_TYPE;
    }
}
