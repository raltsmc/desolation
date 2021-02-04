package raltsmc.desolation.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.registry.DesolationBlockEntities;
import raltsmc.desolation.registry.DesolationBlocks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class AshSiphonBlockEntity extends BlockEntity implements Tickable, IAnimatable {
    private boolean isActive;
    private int radius = 5;
    private int dHeight = 2;
    private float tickPercentChance = 0.7f;
    private final Random random;

    private boolean correctBiome;

    private AnimationFactory factory = new AnimationFactory(this);

    public AshSiphonBlockEntity() {
        super(DesolationBlockEntities.ASH_SIPHON_BLOCK_ENTITY);
        isActive = false;
        random = new Random();
    }

    @Override
    public void tick() {
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
            if (!world.isClient) {
                float b = random.nextFloat() * 100;
                if (isActive && b < tickPercentChance) {
                    List<BlockPos> openBlocks = new ArrayList<>();
                    BlockPos.streamOutwards(pos, radius, dHeight, radius)
                            .filter(blockPos -> (world.getBlockState(blockPos.down()).isSolidBlock(world, blockPos.down()) || (world.getBlockState(blockPos.down()).getBlock() == DesolationBlocks.ASH_LAYER_BLOCK && world.getBlockState(blockPos.down()).get(Properties.LAYERS) == 8)) && (world.getBlockState(blockPos).isAir() || (world.getBlockState(blockPos).getBlock() == DesolationBlocks.ASH_LAYER_BLOCK && world.getBlockState(blockPos).get(Properties.LAYERS) < 8)))
                            .forEach(e -> openBlocks.add(e.toImmutable()));

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
            } else {
                if (isActive) {
                    ParticleEffect particleEffect = ParticleTypes.WHITE_ASH;
                    float f, g, h;
                    if (random.nextBoolean()) {
                        for (int i = 0; i < 45; ++i) {
                            f = random.nextFloat() * radius * 2 - radius;
                            g = random.nextFloat() * dHeight * 2 - dHeight;
                            h = random.nextFloat() * radius * 2 - radius;

                            world.addParticle(particleEffect, pos.getX() + (double)f, pos.getY() + (double)g, pos.getZ() + (double)h, 0, -0.1, 0);
                        }
                    }
                }
            }
        }
    }

    private <E extends IAnimatable> PlayState spinPredicateInner(AnimationEvent<E> event) {
        if (getWorld().isReceivingRedstonePower(pos)) {
            event.getController().setAnimation(new AnimationBuilder()
                .addAnimation("animation.ash_siphon.spin_inner", true));
            return PlayState.CONTINUE;
        } else {
            return PlayState.STOP;
        }
    }

    private <E extends IAnimatable> PlayState spinPredicateOuter(AnimationEvent<E> event) {
        if (getWorld().isReceivingRedstonePower(pos)) {
            event.getController().setAnimation(new AnimationBuilder()
                    .addAnimation("animation.ash_siphon.spin_outer", true));
            return PlayState.CONTINUE;
        } else {
            return PlayState.STOP;
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "innerSpinController", 120, this::spinPredicateInner));
        animationData.addAnimationController(new AnimationController(this, "outerSpinController", 80, this::spinPredicateOuter));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
