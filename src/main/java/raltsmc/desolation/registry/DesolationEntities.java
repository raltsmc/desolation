package raltsmc.desolation.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.Heightmap;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.entity.AshScuttlerEntity;
import raltsmc.desolation.entity.BlackenedEntity;

public final class DesolationEntities {

    public static final EntityType<AshScuttlerEntity> ASH_SCUTTLER = Registry.register(
            Registries.ENTITY_TYPE,
            Desolation.id("ash_scuttler"),
            FabricEntityTypeBuilder.<AshScuttlerEntity>create(SpawnGroup.AMBIENT, AshScuttlerEntity::new)
                    .dimensions(EntityDimensions.fixed(0.56f,0.32f))
                    .fireImmune()
                    .build()
    );

    public static final EntityType<BlackenedEntity> BLACKENED = Registry.register(
            Registries.ENTITY_TYPE,
            Desolation.id("blackened"),
            FabricEntityTypeBuilder.<BlackenedEntity>create(SpawnGroup.MONSTER, BlackenedEntity::new)
                    .dimensions(EntityDimensions.fixed(0.75f,2f))
                    .fireImmune()
                    .build()
    );

    static void init() {
        FabricDefaultAttributeRegistry.register(ASH_SCUTTLER, AshScuttlerEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(BLACKENED, BlackenedEntity.createBlackenedAttributes());

        SpawnRestriction.register(ASH_SCUTTLER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
        SpawnRestriction.register(BLACKENED, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canMobSpawn);
    }
}
