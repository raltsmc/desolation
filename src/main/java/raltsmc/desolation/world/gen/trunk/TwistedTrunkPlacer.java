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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.registry.DesolationTrunkPlacerTypes;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TwistedTrunkPlacer extends TrunkPlacer {
    private final int maxRadius = 3;
    private final int startLowerBound = 2;

    public TwistedTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    public static final Codec<TwistedTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> method_28904(instance).apply(instance, TwistedTrunkPlacer::new));

    @Override
    protected TrunkPlacerType<?> getType() {
        return DesolationTrunkPlacerTypes.TWISTED;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> set, BlockBox blockBox, TreeFeatureConfig treeFeatureConfig) {
        int startRadius = random.nextInt(maxRadius - startLowerBound + 1) + startLowerBound;
        int sX = random.nextInt(3) - 1;
        int sZ = random.nextInt(3) - 1;
        System.out.println("INITIALIZING --------- [ Start Pos: " + pos + " | Trunk Height: " + trunkHeight + " | Start Radius: " + startRadius + " ] ---------");

        float tickerX = 0;
        float tickerZ = 0;

        BlockPos.Mutable currentPos = pos.mutableCopy();
        int radius = startRadius;

        for (int i = 0; i < trunkHeight; i++) {
            int finalRadius = radius;
            BlockBox radiusBox = BlockBox.create(
                    currentPos.getX() - (radius+sX), currentPos.getY(), currentPos.getZ() - (radius+sZ),
                    currentPos.getX() + (radius+sX), currentPos.getY(), currentPos.getZ() + (radius+sZ)
            );
            System.out.println("Block Box: " + radiusBox);
            //Stream<BlockPos> blocks = BlockPos.streamOutwards(currentPos, MathHelper.clamp(radius + sX, 1, startRadius + sX), 0, MathHelper.clamp(radius + sY, 1, startRadius + sY))
            BlockPos.stream(radiusBox)
                    .filter(e -> e.isWithinDistance(currentPos, (double) finalRadius))
                    .forEach(pos1 -> {
                        if (canReplace(world, pos1) && isSolidOrTouching(world, pos1.down())) {
                            placeAt(world, random, pos1, Direction.UP, set, blockBox, treeFeatureConfig);
                        }
                    });
            /*for (BlockPos pos1 : blocks) {
                if (canReplace(world, pos1) && isSolidOrTouching(world, pos1.down())) {
                    placeAt(world, random, pos1, Direction.UP, set, blockBox, treeFeatureConfig);
                }
            }*/
            //System.out.println("Stream Size: " + blocks.count());

            radius = MathHelper.clamp(radius + random.nextInt(3) - 1, 2, maxRadius);
            tickerX += (float)Math.sin(i);
            tickerZ += (float)Math.cos(i);
            currentPos.move(Math.round(tickerX), 1, Math.round(tickerZ));
            System.out.println("New Radius: " + radius + " | New Pos: " + currentPos);
        }

        return ImmutableList.of(new FoliagePlacer.TreeNode(currentPos, 0, false));
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

    protected static boolean isSolidOrTouching(ModifiableTestableWorld world, BlockPos pos) {
        final BlockView view = (BlockView)world;
        return (
                world.testBlockState(pos, e -> (
                        e.isSolidBlock(view, pos)) ||
                        e.isSideSolidFullSquare(view, pos, Direction.NORTH) ||
                        e.isSideSolidFullSquare(view, pos, Direction.SOUTH) ||
                        e.isSideSolidFullSquare(view, pos, Direction.EAST) ||
                        e.isSideSolidFullSquare(view, pos, Direction.WEST)
                )
        );
    }

}
