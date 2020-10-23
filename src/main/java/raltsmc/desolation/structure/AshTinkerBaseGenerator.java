package raltsmc.desolation.structure;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.*;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.DesolationMod;

import java.util.List;
import java.util.Random;

public class AshTinkerBaseGenerator {
    private static final Identifier TINKER_BASE_MAIN = Desolation.id("ash_tinker_base/ash_tinker_base");

    public static void addPieces(StructureManager manager, BlockPos pos, BlockRotation rotation,
                                 List<StructurePiece> pieces) {
        pieces.add(new Piece(manager, pos, TINKER_BASE_MAIN, rotation));
    }

    public static class Piece extends SimpleStructurePiece {
        private final BlockRotation rotation;
        private final Identifier template;

        public Piece(StructureManager structureManager, CompoundTag compoundTag) {
            super(DesolationMod.TINKER_BASE_PIECE, compoundTag);
            this.template = new Identifier(compoundTag.getString("Template"));
            this.rotation = BlockRotation.valueOf(compoundTag.getString("Rot"));
            this.initializeStructureData(structureManager);
        }

        public Piece(StructureManager structureManager, BlockPos pos, Identifier template, BlockRotation rotation) {
            super(DesolationMod.TINKER_BASE_PIECE, 0);
            this.pos = pos;
            this.rotation = rotation;
            this.template = template;
            this.initializeStructureData(structureManager);
        }

        private void initializeStructureData(StructureManager structureManager) {
            Structure structure = structureManager.getStructureOrBlank(this.template);
            StructurePlacementData placementData = (new StructurePlacementData())
                    .setRotation(this.rotation)
                    .setMirror(BlockMirror.NONE)
                    .addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
            this.setStructureData(structure, this.pos, placementData);
        }

        protected void toNbt(CompoundTag tag) {
            super.toNbt(tag);
            tag.putString("Template", this.template.toString());
            tag.putString("Rot", this.rotation.name());
        }

        @Override
        protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess serverWorldAccess,
                                      Random random, BlockBox boundingBox) {
            if ("chest".equals(metadata)) {
                serverWorldAccess.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = serverWorldAccess.getBlockEntity(pos.down());
                if (blockEntity instanceof ChestBlockEntity) {
                    ((ChestBlockEntity)blockEntity).setLootTable(Desolation.id("chests/ash_tinker_base"), random.nextLong());
                }
            }
        }

        /*public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor,
                                 ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            StructurePlacementData placementData = (new StructurePlacementData()).setRotation(this.rotation).setMirror(BlockMirror.NONE).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
            BlockPos blockPos2 = BlockPos.ORIGIN;
            BlockPos blockPos3 = this.pos.add(Structure.transform(placementData, new BlockPos(3-blockPos2.getX(), 0, -blockPos2.getZ())));
            int i = structureWorldAccess.getTopY(Heightmap.Type.WORLD_SURFACE_WG, blockPos3.getX(), blockPos3.getZ());
            BlockPos blockPos4 = this.pos;
            this.pos = this.pos.add(0, i-90-1, 0);
            boolean bl = super.generate(structureWorldAccess, structureAccessor, chunkGenerator, random, boundingBox,
                    chunkPos, blockPos);
            this.pos = blockPos4;
            return bl;
        }*/
    }
}
