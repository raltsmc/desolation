package raltsmc.desolation.block.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.registry.DesolationBlockEntities;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class AshSiphonBlockEntity extends BlockEntity implements Tickable {
    private boolean isActive;
    private int radius;
    private int dHeight;
    private float tickPercentChance = 1.5f;
    private final Random random;

    private boolean correctBiome;

    public AshSiphonBlockEntity() {
        super(DesolationBlockEntities.ASH_SIPHON_BLOCK_ENTITY);
        isActive = false;
        random = new Random();

        Identifier currentBiome = this.world.getRegistryManager().get(Registry.BIOME_KEY).getId(this.world.getBiome(pos));
        correctBiome =
                ((Objects.equals(currentBiome, Desolation.id("charred_forest"))
                || Objects.equals(currentBiome, Desolation.id("charred_forest_small"))
                || Objects.equals(currentBiome, Desolation.id("charred_forest_clearing")))
                && this.pos.getY() >= world.getSeaLevel() - 10);
    }

    @Override
    public void tick() {
        if (correctBiome) {
            if (getWorld().isReceivingRedstonePower(pos)) {
                isActive = true;
            } else {
                isActive = false;
            }

            if (isActive && random.nextInt(100) < tickPercentChance) {
                List<BlockPos> openBlocks;
                //BlockPos.findClosest()
            }
        }
    }
}
