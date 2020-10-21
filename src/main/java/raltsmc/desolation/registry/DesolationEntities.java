package raltsmc.desolation.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.entity.AshScuttlerEntity;
import raltsmc.desolation.entity.BlackenedEntity;

public final class DesolationEntities {

    public static final EntityType<AshScuttlerEntity> ASH_SCUTTLER = Registry.register(
            Registry.ENTITY_TYPE,
            Desolation.id("ash_scuttler"),
            FabricEntityTypeBuilder.<AshScuttlerEntity>create(SpawnGroup.CREATURE, AshScuttlerEntity::new)
                    .dimensions(EntityDimensions.fixed(0.56f,0.32f))
                    .fireImmune()
                    .specificSpawnBlocks(DesolationBlocks.CHARRED_SOIL)
                    .build()
    );

    public static final EntityType<BlackenedEntity> BLACKENED = Registry.register(
            Registry.ENTITY_TYPE,
            Desolation.id("blackened"),
            FabricEntityTypeBuilder.<BlackenedEntity>create(SpawnGroup.MONSTER, BlackenedEntity::new)
                    .dimensions(EntityDimensions.fixed(0.75f,2f))
                    .fireImmune()
                    .specificSpawnBlocks(DesolationBlocks.CHARRED_SOIL)
                    .build()
    );

    static void init() {
        FabricDefaultAttributeRegistry.register(ASH_SCUTTLER, AshScuttlerEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(BLACKENED, BlackenedEntity.createBlackenedAttributes());
    }
}
