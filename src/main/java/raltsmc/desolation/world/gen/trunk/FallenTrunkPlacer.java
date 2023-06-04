package raltsmc.desolation.world.gen.trunk;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.registry.DesolationTrunkPlacerTypes;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class FallenTrunkPlacer extends StraightTrunkPlacer {
    public static Predicate<BlockState> REPLACEABLE_PREDICATE = BlockStatePredicate.forBlock(DesolationBlocks.ASH_BLOCK)
            .or(BlockStatePredicate.forBlock(DesolationBlocks.ASH_LAYER_BLOCK))
            .or(BlockStatePredicate.forBlock(DesolationBlocks.EMBER_BLOCK));

    public FallenTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    public static final Codec<FallenTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> fillTrunkPlacerFields(instance).apply(instance, FallenTrunkPlacer::new));

    @Override
    protected TrunkPlacerType<?> getType() {
        return DesolationTrunkPlacerTypes.FALLEN;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer,
                                                 Random random, int height, BlockPos startPos,
                                                 TreeFeatureConfig config) {
        List<FoliagePlacer.TreeNode> treeNodes = Lists.newArrayList();

        Direction.Axis placementAxis = random.nextBoolean() ? Direction.Axis.X : Direction.Axis.Z;
        Direction placementDirection = Direction.from(placementAxis, random.nextBoolean() ?
                Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE);

        BlockPos.Mutable currentPos = startPos.mutableCopy();

        int supportedAndFree = 0;
        for (int i = 0; i < height; ++i) {
            if (!world.testBlockState(startPos.offset(placementDirection, i).down(),
                    BlockStatePredicate.forBlock(Blocks.AIR).or(BlockStatePredicate.forBlock(Blocks.WATER)))
                    && !world.testBlockState(startPos.offset(placementDirection, i).down(), (state) -> {
                Material material = state.getMaterial();
                return material == Material.REPLACEABLE_PLANT; })
                    && canReplace(world, startPos)) {
                supportedAndFree++;
            }
        }

        if (supportedAndFree / (float)height > 0.6) {
            for (int i = 0; i < height; ++i) {
                currentPos.move(placementDirection);
                if (world.testBlockState(currentPos, BlockStatePredicate.forBlock(DesolationBlocks.CHARRED_SOIL)
                        .or(BlockStatePredicate.forBlock(DesolationBlocks.CHARRED_LOG)))) { break; }
                placeTrunkBlock(world, replacer, random, currentPos, config, placementAxis, treeNodes);
            }
        }

        return ImmutableList.of(new FoliagePlacer.TreeNode(currentPos, 0, false));
    }

    protected boolean placeTrunkBlock(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos blockPos, TreeFeatureConfig treeFeatureConfig, Direction.Axis axis, List<FoliagePlacer.TreeNode> treeNodes) {
        if (TreeFeature.canReplace(world, blockPos)) {
            replacer.accept(blockPos, treeFeatureConfig.trunkProvider.get(random, blockPos).with(PillarBlock.AXIS, axis));
            treeNodes.add(new FoliagePlacer.TreeNode(blockPos.toImmutable(), 0, false));
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean canReplace(TestableWorld world, BlockPos pos) {
        return TreeFeature.canReplace(world, pos) || world.testBlockState(pos, REPLACEABLE_PREDICATE);
    }

}
