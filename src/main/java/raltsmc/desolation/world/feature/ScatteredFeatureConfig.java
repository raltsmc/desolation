package raltsmc.desolation.world.feature;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.placer.BlockPlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ScatteredFeatureConfig implements FeatureConfig {
    public static final Codec<ScatteredFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BlockStateProvider.TYPE_CODEC.fieldOf("state_provider").forGetter((scatteredFeatureConfig) -> {
            return scatteredFeatureConfig.stateProvider;
        }), BlockPlacer.TYPE_CODEC.fieldOf("block_placer").forGetter((scatteredFeatureConfig) -> {
            return scatteredFeatureConfig.blockPlacer;
        }), BlockState.CODEC.listOf().fieldOf("whitelist").forGetter((scatteredFeatureConfig) -> {
            return (List)scatteredFeatureConfig.whitelist.stream().map(Block::getDefaultState).collect(Collectors.toList());
        }), BlockState.CODEC.listOf().fieldOf("blacklist").forGetter((scatteredFeatureConfig) -> {
            return ImmutableList.copyOf(scatteredFeatureConfig.blacklist);
        }), Codec.INT.fieldOf("tries").orElse(128).forGetter((scatteredFeatureConfig) -> {
            return scatteredFeatureConfig.tries;
        }), Codec.INT.fieldOf("xspread").orElse(7).forGetter((scatteredFeatureConfig) -> {
            return scatteredFeatureConfig.spreadX;
        }), Codec.INT.fieldOf("yspread").orElse(3).forGetter((scatteredFeatureConfig) -> {
            return scatteredFeatureConfig.spreadY;
        }), Codec.INT.fieldOf("zspread").orElse(7).forGetter((scatteredFeatureConfig) -> {
            return scatteredFeatureConfig.spreadZ;
        }), Codec.DOUBLE.fieldOf("fail_chance").orElse(0d).forGetter((scatteredFeatureConfig) -> {
            return scatteredFeatureConfig.failChance;
        }), Codec.BOOL.fieldOf("can_replace").orElse(false).forGetter((scatteredFeatureConfig) -> {
            return scatteredFeatureConfig.canReplace;
        }), Codec.BOOL.fieldOf("project").orElse(true).forGetter((scatteredFeatureConfig) -> {
            return scatteredFeatureConfig.project;
        }), Codec.BOOL.fieldOf("need_water").orElse(false).forGetter((scatteredFeatureConfig) -> {
            return scatteredFeatureConfig.needsWater;
        }), Codec.BOOL.fieldOf("modify_ground").orElse(false).forGetter((scatteredFeatureConfig) -> {
            return scatteredFeatureConfig.modifyGround;
        }), Codec.BOOL.fieldOf("gen_in_water").orElse(false).forGetter((scatteredFeatureConfig) -> {
            return scatteredFeatureConfig.genInWater;
        })).apply(instance, ScatteredFeatureConfig::new);
    });
    public final BlockStateProvider stateProvider;
    public final BlockPlacer blockPlacer;
    public final Set<Block> whitelist;
    public final Set<BlockState> blacklist;
    public final int tries;
    public final int spreadX;
    public final int spreadY;
    public final int spreadZ;
    public final double failChance;
    public final boolean canReplace;
    public final boolean project;
    public final boolean needsWater;
    public final boolean modifyGround;
    public final boolean genInWater;

    private ScatteredFeatureConfig(BlockStateProvider stateProvider, BlockPlacer blockPlacer,
                                   List<BlockState> whitelist, List<BlockState> blacklist, int tries, int spreadX,
                                   int spreadY, int spreadZ, double failChance, boolean canReplace, boolean project,
                                   boolean needsWater, boolean modifyGround, boolean genInWater) {
        this(stateProvider, blockPlacer, (Set)((Set)whitelist.stream().map(AbstractBlock.AbstractBlockState::getBlock).collect(Collectors.toSet())), (Set) ImmutableSet.copyOf(blacklist), tries, spreadX, spreadY, spreadZ, failChance, canReplace, project, needsWater, modifyGround, genInWater);
    }

    private ScatteredFeatureConfig(BlockStateProvider stateProvider, BlockPlacer blockPlacer, Set<Block> whitelist,
                                   Set<BlockState> blacklist, int tries, int spreadX, int spreadY, int spreadZ,
                                   double failChance, boolean canReplace, boolean project, boolean needsWater,
                                   boolean modifyGround, boolean genInWater) {
        this.stateProvider = stateProvider;
        this.blockPlacer = blockPlacer;
        this.whitelist = whitelist;
        this.blacklist = blacklist;
        this.tries = tries;
        this.spreadX = spreadX;
        this.spreadY = spreadY;
        this.spreadZ = spreadZ;
        this.failChance = failChance;
        this.canReplace = canReplace;
        this.project = project;
        this.needsWater = needsWater;
        this.modifyGround = modifyGround;
        this.genInWater = genInWater;
    }

    public static class Builder {
        private final BlockStateProvider stateProvider;
        private final BlockPlacer blockPlacer;
        private Set<Block> whitelist = ImmutableSet.of();
        private Set<BlockState> blacklist = ImmutableSet.of();
        private int tries = 64;
        private int spreadX = 7;
        private int spreadY = 3;
        private int spreadZ = 7;
        private double failChance = 0d;
        private boolean canReplace;
        private boolean project = true;
        private boolean needsWater = false;
        private boolean modifyGround = false;
        private boolean genInWater = false;

        public Builder(BlockStateProvider stateProvider, BlockPlacer blockPlacer) {
            this.stateProvider = stateProvider;
            this.blockPlacer = blockPlacer;
        }

        public Builder whitelist(Set<Block> whitelist) {
            this.whitelist = whitelist;
            return this;
        }

        public Builder blacklist(Set<BlockState> blacklist) {
            this.blacklist = blacklist;
            return this;
        }

        public Builder tries(int tries) {
            this.tries = tries;
            return this;
        }

        public Builder spreadX(int spreadX) {
            this.spreadX = spreadX;
            return this;
        }

        public Builder spreadY(int spreadY) {
            this.spreadY = spreadY;
            return this;
        }

        public Builder spreadZ(int spreadZ) {
            this.spreadZ = spreadZ;
            return this;
        }

        public Builder failChance(double failChance) {
            this.failChance = failChance;
            return this;
        }

        public Builder canReplace() {
            this.canReplace = true;
            return this;
        }

        public Builder cannotProject() {
            this.project = false;
            return this;
        }

        public Builder needsWater() {
            this.needsWater = true;
            return this;
        }

        public Builder modifyGround() {
            this.modifyGround = true;
            return this;
        }

        public Builder genInWater() {
            this.genInWater = true;
            return this;
        }

        public ScatteredFeatureConfig build() {
            return new ScatteredFeatureConfig(this.stateProvider, this.blockPlacer, this.whitelist, this.blacklist,
                    this.tries, this.spreadX, this.spreadY, this.spreadZ, this.failChance, this.canReplace,
                    this.project, this.needsWater, this.modifyGround, this.genInWater);
        }
    }
}

