package raltsmc.desolation.world.gen.trunk;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
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

import java.util.*;
import java.util.function.Predicate;

public class BasedTrunkPlacer extends StraightTrunkPlacer {
    public BasedTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    public static final Codec<BasedTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> fillTrunkPlacerFields(instance).apply(instance, BasedTrunkPlacer::new));

    @Override
    protected TrunkPlacerType<?> getType() {
        return DesolationTrunkPlacerTypes.BASED;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> set, BlockBox blockBox, TreeFeatureConfig treeFeatureConfig) {
        Direction placementDirection = Direction.UP;

        BlockPos.Mutable currentPos = pos.mutableCopy();
        int maxBaseHeight = 2;

        for (int i = 0; i < trunkHeight; ++i) {
            placeAt(world, random, currentPos, placementDirection, set, blockBox, treeFeatureConfig);
            if (i == 0) {
                generateBase(world, random, currentPos, placementDirection, set, blockBox, treeFeatureConfig, maxBaseHeight);
            }
            currentPos.move(Direction.UP);
        }

        return ImmutableList.of(new FoliagePlacer.TreeNode(currentPos, 0, false));
    }

    protected static void generateBase(ModifiableTestableWorld world, Random random, BlockPos pos, Direction direction,
                                       Set<BlockPos> set, BlockBox blockBox, TreeFeatureConfig treeFeatureConfig,
                                       int maxBaseHeight) {
        List<Direction> dirs = Arrays.asList(Direction.values());
        Collections.shuffle(dirs);
        for (Direction dir : dirs) {
            int height = random.nextInt(maxBaseHeight + 1);
            if (height > 0 && dir != Direction.UP && dir != Direction.DOWN && random.nextInt(3) > 0) {
                BlockPos.Mutable startPos = pos.offset(dir).mutableCopy();
                if (!canReplace(world, startPos.down())) {
                    for (int j = 0; j < height; j++) {
                        placeAt(world, random, startPos.up(j), direction, set, blockBox, treeFeatureConfig);
                    }
                    if (random.nextBoolean()) {
                        maxBaseHeight--;
                    }
                }
            }
        }
    }

    protected static void placeAt(ModifiableTestableWorld world, Random random, BlockPos pos, Direction direction,
                                  Set<BlockPos> set, BlockBox blockBox, TreeFeatureConfig treeFeatureConfig) {
        setBlockState(world, pos, treeFeatureConfig.trunkProvider.getBlockState(random, pos).with(PillarBlock.AXIS,
                direction.getAxis()), blockBox);
        set.add(pos.toImmutable());
    }

    protected static boolean canReplace(ModifiableTestableWorld world, BlockPos pos) {
        Predicate<BlockState> REPLACEABLE_PREDICATE = BlockStatePredicate.forBlock(DesolationBlocks.ASH_BLOCK)
                .or(BlockStatePredicate.forBlock(DesolationBlocks.ASH_LAYER_BLOCK))
                .or(BlockStatePredicate.forBlock(DesolationBlocks.EMBER_BLOCK));
        return TreeFeature.canReplace(world, pos) || world.testBlockState(pos, REPLACEABLE_PREDICATE);
    }

}
