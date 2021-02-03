package raltsmc.desolation.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.registry.DesolationBlockEntities;
import raltsmc.desolation.registry.DesolationBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class AshSiphonBlockEntity extends BlockEntity implements Tickable {
    private boolean isActive;
    private int radius = 5;
    private int dHeight = 2;
    private float tickPercentChance = 0.7f;
    private final Random random;

    private boolean correctBiome;

    public AshSiphonBlockEntity() {
        super(DesolationBlockEntities.ASH_SIPHON_BLOCK_ENTITY);
        isActive = false;
        random = new Random();
    }

    @Override
    public void tick() {
        if (!world.isClient) {
            Identifier currentBiome = this.getWorld().getRegistryManager().get(Registry.BIOME_KEY).getId(this.world.getBiome(pos));
            correctBiome =
                    ((Objects.equals(currentBiome, Desolation.id("charred_forest"))
                            || Objects.equals(currentBiome, Desolation.id("charred_forest_small"))
                            || Objects.equals(currentBiome, Desolation.id("charred_forest_clearing")))
                            && this.pos.getY() >= world.getSeaLevel() - 10);
            if (correctBiome) {
                if (getWorld().isReceivingRedstonePower(pos)) {
                    isActive = true;
                } else {
                    isActive = false;
                }

                float b = random.nextFloat() * 100;
                if (isActive && b < tickPercentChance) {
                    List<BlockPos> openBlocks = new ArrayList<>();
                    BlockPos.stream(pos.getX() - radius, pos.getY() - dHeight, pos.getZ() - radius, pos.getX() + radius, pos.getY() + dHeight, pos.getZ() + radius)
                            .filter(blockPos -> world.getBlockState(blockPos.down()).isSolidBlock(world, blockPos.down()) && (world.getBlockState(blockPos).isAir() || (world.getBlockState(blockPos).getBlock() == DesolationBlocks.ASH_LAYER_BLOCK && world.getBlockState(blockPos).get(Properties.LAYERS) < 8)))
                            .forEach(e -> openBlocks.add(e.toImmutable()));
                    for (int i = openBlocks.size() - 1; i >= 0; i--) {
                        BlockPos blockPos = openBlocks.get(i);
                        if (!(world.getBlockState(blockPos.down()).isSolidBlock(world, blockPos.down())
                                && (!world.getBlockState(blockPos).isSolidBlock(world, blockPos) || (world.getBlockState(blockPos).getBlock() == DesolationBlocks.ASH_LAYER_BLOCK && world.getBlockState(blockPos).get(Properties.LAYERS) < 8)))) {
                            openBlocks.remove(i);
                        }
                    }

                    if (openBlocks.size() > 0) {
                        BlockPos toUpdate = openBlocks.get(random.nextInt(openBlocks.size()));
                        BlockState stateToUpdate = world.getBlockState(toUpdate);

                        if (stateToUpdate.getBlock() == DesolationBlocks.ASH_LAYER_BLOCK) {
                            int newLayers = stateToUpdate.get(Properties.LAYERS) + 1;
                            if (newLayers > 8) newLayers = 8;
                            world.setBlockState(toUpdate, stateToUpdate.with(Properties.LAYERS, newLayers));
                        } else {
                            world.setBlockState(toUpdate, DesolationBlocks.ASH_LAYER_BLOCK.getDefaultState());
                        }
                    }
                }
            }
        }
    }
}
