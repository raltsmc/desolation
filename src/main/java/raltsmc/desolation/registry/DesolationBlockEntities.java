package raltsmc.desolation.registry;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.block.entity.AshSiphonBlockEntity;

import java.util.function.Supplier;

public class DesolationBlockEntities {
    public static BlockEntityType<AshSiphonBlockEntity> ASH_SIPHON_BLOCK_ENTITY = register(AshSiphonBlockEntity::new, DesolationBlocks.ASH_SIPHON, "ash_siphon_block_entity");

    public static BlockEntityType register(Supplier<BlockEntity> blockEntitySupplier, Block block, String path) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Desolation.id(path), BlockEntityType.Builder.create(blockEntitySupplier, block).build(null));
    }
}
