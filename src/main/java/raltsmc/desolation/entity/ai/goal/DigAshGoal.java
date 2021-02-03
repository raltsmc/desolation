package raltsmc.desolation.entity.ai.goal;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.WeightedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import raltsmc.desolation.entity.AshScuttlerEntity;
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.registry.DesolationItems;

import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Predicate;

public class DigAshGoal extends MoveToTargetPosGoal {
    private static final Predicate<BlockState> ASH_PREDICATE;
    private static final WeightedList<Item> ASH_LOOT_TABLE;
    private final AshScuttlerEntity mob;
    private final World world;
    private final int range;
    private final int maxDY;
    private int digTick;
    private boolean reached;

    public DigAshGoal(AshScuttlerEntity mob, double speed, int range, int maxDY) {
        super(mob, speed, range, maxDY);
        this.mob = mob;
        this.world = mob.world;
        this.range = range;
        this.maxDY = maxDY;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK, Control.JUMP));
    }

    public boolean canStart() {
        return this.mob.isSearching() && this.getNearestBlock(this.mob.getBlockPos(), range, maxDY);
    }

    public void start() {
        super.start();
        this.digTick = 0;
    }

    public void stop() {
        super.stop();
        this.mob.setSearching(false);
    }

    public void tick() {
        BlockPos blockPos = this.getTargetPos();
        if (!blockPos.isWithinDistance(this.mob.getPos(), getDesiredSquaredDistanceToTarget())) {
            this.reached = false;
            ++this.tryingTime;
            Vec3d vel = this.mob.getVelocity();
            if (this.shouldResetPath() && new Vec3d(vel.x, 0, vel.z).lengthSquared() < 0.2f) {
                this.mob.setVelocity(vel.x, 0.5f, vel.z);
                this.mob.getNavigation().startMovingTo((double)((float)blockPos.getX()) + 0.5D, (double)blockPos.getY(), (double)((float)blockPos.getZ()) + 0.5D, this.speed);
            }
        } else {
            this.reached = true;
            --this.tryingTime;
        }
        if (this.reached) {
            digTick++;
            if (digTick >= 20) {
                if (!world.isClient) {
                    world.breakBlock(targetPos, false, this.mob, 1);
                    world.syncWorldEvent(2001, targetPos, 0);
                    ItemScatterer.spawn(world, targetPos.getX() + 0.5D, targetPos.getY() + 0.5D, targetPos.getZ() + 0.5D,
                            new ItemStack(ASH_LOOT_TABLE.pickRandom(world.random)));
                }
                stop();
            }
        }
    }

    public double getDesiredSquaredDistanceToTarget() {
        return 2.0D;
    }

    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return ASH_PREDICATE.test(world.getBlockState(pos));
    }

    protected boolean getNearestBlock(BlockPos startPos, int maxRange, int maxDY) {
        Optional<BlockPos> closestAsh = BlockPos.findClosest(startPos, maxRange, maxDY,
                (blockPos) -> world.getBlockState(blockPos).getBlock() == DesolationBlocks.ASH_LAYER_BLOCK
                        || world.getBlockState(blockPos).getBlock() == DesolationBlocks.ASH_BLOCK);
        if (closestAsh.isPresent()) {
            this.targetPos = closestAsh.get();
            return true;
        }
        if (world.isClient) {
            double pVel = world.random.nextGaussian() * 0.02D;
            world.addParticle(ParticleTypes.SMOKE, mob.getX(), mob.getY(), mob.getZ(), pVel, pVel, pVel);
        } else {
            ItemScatterer.spawn(world, mob.getX(), mob.getY(), mob.getZ(),
                    new ItemStack(DesolationItems.CINDERFRUIT));
        }
        stop();
        return false;
    }

    static {
        ASH_PREDICATE = BlockStatePredicate.forBlock(DesolationBlocks.ASH_LAYER_BLOCK)
                .or(BlockStatePredicate.forBlock(DesolationBlocks.ASH_BLOCK));
        // TODO make this configurable
        ASH_LOOT_TABLE = new WeightedList<Item>()
                .add(Items.GLASS, 240)
                .add(Items.OAK_WOOD, 280)
                .add(Items.BONE, 232)
                .add(Items.EGG, 160)
                .add(Items.FLINT, 160)
                .add(Items.STRING, 160)
                .add(Items.GUNPOWDER, 160)
                .add(Items.IRON_INGOT, 160)
                .add(Items.EMERALD, 80)
                .add(Items.LEATHER, 200)
                .add(Items.LEAD, 56)
                .add(Items.NAME_TAG, 56)
                .add(Items.SUGAR_CANE, 120)
                .add(Items.ENDER_PEARL, 64)
                .add(Items.FIRE_CHARGE, 40)
                .add(Items.DIAMOND, 10)
                .add(Items.ENCHANTED_GOLDEN_APPLE, 4)
                .add(Items.TOTEM_OF_UNDYING, 1);
    }
}
