package raltsmc.desolation.entity.ai.goal;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.WeightedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.entity.AshScuttlerEntity;
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.registry.DesolationItems;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DigAshGoal extends MoveToTargetPosGoal {
    private static final Predicate<BlockState> ASH_PREDICATE;
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

                    Identifier id = Desolation.id("misc/ash_scuttler_dig");
                    LootTable lootTable = this.world.getServer().getLootManager().getTable(id);
                    LootContext.Builder builder = (new net.minecraft.loot.context.LootContext.Builder((ServerWorld)this.world))
                            .random(this.world.random);
                    lootTable.generateLoot(builder.build(LootContextTypes.EMPTY), itemStack -> {
                        ItemScatterer.spawn(world, targetPos.getX() + 0.5D, targetPos.getY() + 0.5D, targetPos.getZ() + 0.5D, itemStack);
                    });
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
    }
}
