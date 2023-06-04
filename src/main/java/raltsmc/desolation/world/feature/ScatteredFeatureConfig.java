package raltsmc.desolation.world.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class ScatteredFeatureConfig implements FeatureConfig {
    public static final Codec<ScatteredFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BlockStateProvider.TYPE_CODEC.fieldOf("state_provider").forGetter((scatteredFeatureConfig) -> scatteredFeatureConfig.stateProvider),
            BlockState.CODEC.listOf().fieldOf("whitelist").forGetter((scatteredFeatureConfig) -> ImmutableList.copyOf(scatteredFeatureConfig.whitelist)),
            BlockState.CODEC.listOf().fieldOf("blacklist").forGetter((scatteredFeatureConfig) -> ImmutableList.copyOf(scatteredFeatureConfig.blacklist)),
            Codec.INT.fieldOf("tries").orElse(128).forGetter((scatteredFeatureConfig) -> scatteredFeatureConfig.tries),
            Codec.INT.fieldOf("xspread").orElse(7).forGetter((scatteredFeatureConfig) -> scatteredFeatureConfig.spreadX),
            Codec.INT.fieldOf("yspread").orElse(3).forGetter((scatteredFeatureConfig) -> scatteredFeatureConfig.spreadY),
            Codec.INT.fieldOf("zspread").orElse(7).forGetter((scatteredFeatureConfig) -> scatteredFeatureConfig.spreadZ),
            Codec.DOUBLE.fieldOf("fail_chance").orElse(0d).forGetter((scatteredFeatureConfig) -> scatteredFeatureConfig.failChance),
            Codec.BOOL.fieldOf("can_replace").orElse(false).forGetter((scatteredFeatureConfig) -> scatteredFeatureConfig.canReplace),
            Codec.BOOL.fieldOf("project").orElse(true).forGetter((scatteredFeatureConfig) -> scatteredFeatureConfig.project),
            Codec.BOOL.fieldOf("need_water").orElse(false).forGetter((scatteredFeatureConfig) -> scatteredFeatureConfig.needsWater),
            Codec.BOOL.fieldOf("modify_ground").orElse(false).forGetter((scatteredFeatureConfig) -> scatteredFeatureConfig.modifyGround),
            Codec.BOOL.fieldOf("gen_in_water").orElse(false).forGetter((scatteredFeatureConfig) -> scatteredFeatureConfig.genInWater)
    ).apply(instance, ScatteredFeatureConfig::new));
    public final BlockStateProvider stateProvider;
    public final List<BlockState> whitelist;
    public final List<BlockState> blacklist;
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

    private ScatteredFeatureConfig(BlockStateProvider stateProvider, List<BlockState> whitelist,
                                   List<BlockState> blacklist, int tries, int spreadX, int spreadY, int spreadZ,
                                   double failChance, boolean canReplace, boolean project, boolean needsWater,
                                   boolean modifyGround, boolean genInWater) {
        this.stateProvider = stateProvider;
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
        private List<BlockState> whitelist = List.of();
        private List<BlockState> blacklist = List.of();
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

        public Builder(BlockStateProvider stateProvider) {
            this.stateProvider = stateProvider;
        }

        public Builder whitelist(List<BlockState> whitelist) {
            this.whitelist = whitelist;
            return this;
        }

        public Builder blacklist(List<BlockState> blacklist) {
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
            return new ScatteredFeatureConfig(this.stateProvider, this.whitelist, this.blacklist,
                    this.tries, this.spreadX, this.spreadY, this.spreadZ, this.failChance, this.canReplace,
                    this.project, this.needsWater, this.modifyGround, this.genInWater);
        }
    }
}
