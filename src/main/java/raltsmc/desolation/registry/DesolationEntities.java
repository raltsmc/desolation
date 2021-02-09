package raltsmc.desolation.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.entity.AshFlierEntity;
import raltsmc.desolation.entity.AshScuttlerEntity;
import raltsmc.desolation.entity.BlackenedEntity;

public final class DesolationEntities {

    public static final EntityType<AshScuttlerEntity> ASH_SCUTTLER = Registry.register(
            Registry.ENTITY_TYPE,
            Desolation.id("ash_scuttler"),
            FabricEntityTypeBuilder.<AshScuttlerEntity>create(SpawnGroup.CREATURE, AshScuttlerEntity::new)
                    .dimensions(EntityDimensions.fixed(0.56f,0.32f))
                    .fireImmune()
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

    public static final EntityType<AshFlierEntity> ASH_FLIER = Registry.register(
            Registry.ENTITY_TYPE,
            Desolation.id("ash_flier"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AshFlierEntity::new)
                    .dimensions(EntityDimensions.fixed(1.5f, 0.4f))
                    .fireImmune()
                    .build()
    );

    static void init() {
        FabricDefaultAttributeRegistry.register(ASH_SCUTTLER, AshScuttlerEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(BLACKENED, BlackenedEntity.createBlackenedAttributes());
        FabricDefaultAttributeRegistry.register(ASH_FLIER, AshFlierEntity.createAshFlierAttributes());

        SpawnRestrictionAccessor.callRegister(ASH_SCUTTLER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.WORLD_SURFACE_WG, MobEntity::canMobSpawn);
        SpawnRestrictionAccessor.callRegister(BLACKENED, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canMobSpawn);
        SpawnRestrictionAccessor.callRegister(ASH_FLIER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.WORLD_SURFACE_WG, MobEntity::canMobSpawn);
    }
}
