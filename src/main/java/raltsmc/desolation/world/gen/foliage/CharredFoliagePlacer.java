package raltsmc.desolation.world.gen.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import raltsmc.desolation.registry.DesolationFoliagePlacerTypes;

import java.util.Iterator;
import java.util.Random;
import java.util.function.BiConsumer;

public class CharredFoliagePlacer extends FoliagePlacer {
    public static final Codec<CharredFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) ->
            fillFoliagePlacerFields(instance)
                    .and(IntProvider.createValidatingCodec(1, 512).fieldOf("foliage_height").forGetter(CharredFoliagePlacer::getFoliageHeight))
                    .apply(instance, CharredFoliagePlacer::new)
    );
    protected final IntProvider foliageHeight;

    public CharredFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider foliageHeight) {
        super(radius, offset);
        this.foliageHeight = foliageHeight;
    }

    protected FoliagePlacerType<?> getType() {
        return DesolationFoliagePlacerTypes.CHARRED_FOLIAGE_PLACER;
    }

    public IntProvider getFoliageHeight() {
        return this.foliageHeight;
    }

    @Override
    protected void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {
        for (int i = -foliageHeight; i <= foliageHeight; ++i) {
            int r = radius - (Math.abs(i) / (foliageHeight)) * (radius/2) + i/4;

            BlockPos blockPos = treeNode.getCenter().add(0, i, 0);

            for (BlockPos pos : BlockPos.iterate(blockPos.add(-r, 0, -r), blockPos.add(r, 0, r))) {
                double chance;
                int dX = Math.abs(pos.getX() - blockPos.getX());
                int dZ = Math.abs(pos.getZ() - blockPos.getZ());
                int thisRadius = Math.max(dX, dZ);

                if (thisRadius <= 1) {
                    chance = 0.65;
                } else if (thisRadius <= 2) {
                    chance = 0.4;
                } else {
                    chance = 0.25;
                }

                if (random.nextDouble() < chance && TreeFeature.canReplace(world, pos)) {
                    placeFoliageBlock(world, replacer, random, config, pos);
                }
            }
        }
    }

    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return this.foliageHeight.get(random);
    }

    protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int dy, int dz, boolean giantTrunk) {
        return baseHeight == dz && dy == dz && (random.nextInt(2) == 0 || dx == 0);
    }
}

