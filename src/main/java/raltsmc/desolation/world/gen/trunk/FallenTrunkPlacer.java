package raltsmc.desolation.world.gen.trunk;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.registry.DesolationTrunkPlacerTypes;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

public class FallenTrunkPlacer extends StraightTrunkPlacer {
    public FallenTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    public static final Codec<FallenTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> fillTrunkPlacerFields(instance).apply(instance, FallenTrunkPlacer::new));

    @Override
    protected TrunkPlacerType<?> getType() {
        return DesolationTrunkPlacerTypes.FALLEN;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> set, BlockBox blockBox, TreeFeatureConfig treeFeatureConfig) {
        Direction.Axis placementAxis = random.nextBoolean() ? Direction.Axis.X : Direction.Axis.Z;
        Direction placementDirection = Direction.from(placementAxis, random.nextBoolean() ?
                Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE);

        BlockPos.Mutable currentPos = pos.mutableCopy();

        int supportedAndFree = 0;
        for (int i = 0; i < trunkHeight; ++i) {
            if (!world.testBlockState(pos.offset(placementDirection, i).down(),
                    BlockStatePredicate.forBlock(Blocks.AIR).or(BlockStatePredicate.forBlock(Blocks.WATER)))
                    && !world.testBlockState(pos.offset(placementDirection, i).down(), (state) -> {
                        Material material = state.getMaterial();
                        return material == Material.REPLACEABLE_PLANT; })
                    && canReplace(world, pos)) {
                supportedAndFree++;
            }
        }

        if (supportedAndFree / trunkHeight > 0.6) {
            for (int i = 0; i < trunkHeight; ++i) {
                currentPos.move(placementDirection);
                if (world.testBlockState(currentPos, BlockStatePredicate.forBlock(DesolationBlocks.CHARRED_SOIL)
                        .or(BlockStatePredicate.forBlock(DesolationBlocks.CHARRED_LOG)))) { break; }
                placeAt(world, random, currentPos, placementDirection, set, blockBox,
                        treeFeatureConfig);
            }
        }

        return ImmutableList.of(new FoliagePlacer.TreeNode(currentPos, 0, false));
    }

    protected static void placeAt(ModifiableTestableWorld world, Random random, BlockPos pos, Direction direction,
                                  Set<BlockPos> set, BlockBox blockBox, TreeFeatureConfig treeFeatureConfig) {
        setBlockState(world, pos, treeFeatureConfig.trunkProvider.getBlockState(random, pos).with(PillarBlock.AXIS,
                direction.getAxis()), blockBox);
        set.add(pos.toImmutable());
    }

    /*protected static void tryToPlace(ModifiableTestableWorld world, Random random, BlockPos pos,
                              Direction placementDirection, Set<BlockPos> set,
                              BlockBox blockBox, TreeFeatureConfig treeFeatureConfig) {
        /*if ((TreeFeature.canReplace(world, pos) || canReplace(world, pos)) && !TreeFeature.canReplace(world,
                pos.offset(placementDirection, -1).down(1))) {
            method_27404(world, pos, treeFeatureConfig.trunkProvider.getBlockState(random, pos).with(PillarBlock.AXIS, placementDirection.getAxis()), blockBox);
            set.add(pos.toImmutable());
            return true;
        } else {
            return false;
        }*//*
        if (TreeFeature.canReplace(world, pos)) {
            method_27404(world, pos, treeFeatureConfig.trunkProvider.getBlockState(random, pos).with(PillarBlock.AXIS, placementDirection.getAxis()), blockBox);
            set.add(pos.toImmutable());
        }
    }*/

    protected static boolean canReplace(ModifiableTestableWorld world, BlockPos pos) {
        Predicate<BlockState> REPLACEABLE_PREDICATE = BlockStatePredicate.forBlock(DesolationBlocks.ASH_BLOCK)
                .or(BlockStatePredicate.forBlock(DesolationBlocks.ASH_LAYER_BLOCK))
                .or(BlockStatePredicate.forBlock(DesolationBlocks.EMBER_BLOCK));
        return TreeFeature.canReplace(world, pos) || world.testBlockState(pos, REPLACEABLE_PREDICATE);
    }

}
