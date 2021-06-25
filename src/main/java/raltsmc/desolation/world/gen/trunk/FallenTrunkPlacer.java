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
import net.minecraft.world.TestableWorld;
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
import java.util.function.BiConsumer;
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
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer,
                                                 Random random, int trunkHeight, BlockPos pos,
                                                 TreeFeatureConfig config) {
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

        if ((float)supportedAndFree / (float)trunkHeight > 0.6) {
            for (int i = 0; i < trunkHeight; ++i) {
                currentPos.move(placementDirection);
                if (world.testBlockState(currentPos, BlockStatePredicate.forBlock(DesolationBlocks.CHARRED_SOIL)
                        .or(BlockStatePredicate.forBlock(DesolationBlocks.CHARRED_LOG)))) { break; }
                getAndSetState(world, replacer., random, currentPos, config);
            }
        }

        return ImmutableList.of(new FoliagePlacer.TreeNode(currentPos, 0, false));
    }

    protected static boolean canReplace(TestableWorld world, BlockPos pos) {
        Predicate<BlockState> REPLACEABLE_PREDICATE = BlockStatePredicate.forBlock(DesolationBlocks.ASH_BLOCK)
                .or(BlockStatePredicate.forBlock(DesolationBlocks.ASH_LAYER_BLOCK))
                .or(BlockStatePredicate.forBlock(DesolationBlocks.EMBER_BLOCK));
        return TreeFeature.canReplace(world, pos) || world.testBlockState(pos, REPLACEABLE_PREDICATE);
    }

}
