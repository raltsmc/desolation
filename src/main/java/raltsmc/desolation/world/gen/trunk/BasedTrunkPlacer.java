package raltsmc.desolation.world.gen.trunk;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class BasedTrunkPlacer extends StraightTrunkPlacer {
    public static Predicate<BlockState> REPLACEABLE_PREDICATE = BlockStatePredicate.forBlock(DesolationBlocks.ASH_BLOCK)
            .or(BlockStatePredicate.forBlock(DesolationBlocks.ASH_LAYER_BLOCK))
            .or(BlockStatePredicate.forBlock(DesolationBlocks.EMBER_BLOCK));

    public BasedTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    public static final Codec<BasedTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> fillTrunkPlacerFields(instance).apply(instance, BasedTrunkPlacer::new));

    @Override
    protected TrunkPlacerType<?> getType() {
        return DesolationTrunkPlacerTypes.BASED;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer,
                                                 Random random, int height, BlockPos startPos,
                                                 TreeFeatureConfig config) {
        List<FoliagePlacer.TreeNode> treeNodes = Lists.newArrayList();

        BlockPos.Mutable currentPos = startPos.mutableCopy();
        int maxBaseHeight = 2;

        for (int i = 0; i < height; ++i) {
            placeTrunkBlock(world, replacer, random, currentPos, config, Direction.UP.getAxis(), treeNodes);
            if (i == 0) {
                generateBase(world, replacer, random, currentPos, config, maxBaseHeight, treeNodes);
            }
            currentPos.move(Direction.UP);
        }

        return ImmutableList.of(new FoliagePlacer.TreeNode(currentPos, 0, false));
    }

    protected void generateBase(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer,
                                       Random random, BlockPos pos, TreeFeatureConfig config, int maxBaseHeight,
                                       List<FoliagePlacer.TreeNode> treeNodes) {
        List<Direction> dirs = Arrays.asList(Direction.values());
        Collections.shuffle(dirs);
        for (Direction dir : dirs) {
            int height = random.nextInt(maxBaseHeight + 1);
            if (height > 0 && dir != Direction.UP && dir != Direction.DOWN && random.nextInt(3) > 0) {
                BlockPos.Mutable startPos = pos.offset(dir).mutableCopy();
                if (!canReplace(world, startPos.down())) {
                    for (int j = 0; j < height; j++) {
                        placeTrunkBlock(world, replacer, random, startPos.up(j), config, Direction.UP.getAxis(), treeNodes);
                    }
                    if (random.nextBoolean()) {
                        maxBaseHeight--;
                    }
                }
            }
        }
    }

    protected static boolean placeTrunkBlock(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos blockPos, TreeFeatureConfig treeFeatureConfig, Direction.Axis axis, List<FoliagePlacer.TreeNode> treeNodes) {
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
