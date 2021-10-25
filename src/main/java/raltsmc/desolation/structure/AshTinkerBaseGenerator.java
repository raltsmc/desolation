package raltsmc.desolation.structure;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
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

    public static void addPieces(StructureManager manager, StructurePiecesHolder structurePiecesHolder,
                                 BlockRotation rotation, BlockPos pos) {
        structurePiecesHolder.addPiece(new Piece(manager, TINKER_BASE_MAIN, pos, rotation));
    }

    public static class Piece extends SimpleStructurePiece {
        public Piece(StructureManager manager, Identifier template, BlockPos pos, BlockRotation rotation) {
            super(DesolationMod.TINKER_BASE_PIECE, 0, manager, template, template.toString(),
                    createPlacementData(rotation), pos);
        }

        public Piece(ServerWorld world, NbtCompound nbt) {
            super(DesolationMod.TINKER_BASE_PIECE, nbt, world,
                    (identifier1 -> createPlacementData(BlockRotation.valueOf(nbt.getString("Rot")))));
        }

        private static StructurePlacementData createPlacementData(BlockRotation rotation) {
            return (new StructurePlacementData()).setRotation(rotation).setMirror(BlockMirror.NONE).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
        }

        protected void writeNbt(ServerWorld world, NbtCompound nbt) {
            super.writeNbt(world, nbt);
            nbt.putString("Rot", this.placementData.getRotation().name());
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
