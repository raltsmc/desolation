package raltsmc.desolation.entity.ai.goal;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import raltsmc.desolation.entity.AshScuttlerEntity;
import raltsmc.desolation.registry.DesolationBlocks;

import java.util.EnumSet;
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

    static {
        ASH_PREDICATE = BlockStatePredicate.forBlock(DesolationBlocks.ASH_LAYER_BLOCK)
                .or(BlockStatePredicate.forBlock(DesolationBlocks.ASH_BLOCK));
    }

    public boolean canStart() {
        return this.mob.isSearching() && this.getNearestBlock(this.mob.getBlockPos(), range, maxDY);
    }

    public void start() {
        super.start();
        this.digTick = 0;
        System.out.println("Starting");
    }

    public void stop() {
        super.stop();
        this.mob.setSearching(false);
        System.out.println("Stopping");
    }

    public void tick() {
        BlockPos blockPos = this.getTargetPos();
        if (!blockPos.isWithinDistance(this.mob.getPos(), getDesiredSquaredDistanceToTarget())) {
            this.reached = false;
            System.out.println("False: " + blockPos + " is not within " + getDesiredSquaredDistanceToTarget() + " of " + this.mob.getPos());
            ++this.tryingTime;
            if (this.shouldResetPath()) {
                this.mob.getNavigation().startMovingTo((double)((float)blockPos.getX()) + 0.5D, (double)blockPos.getY(), (double)((float)blockPos.getZ()) + 0.5D, this.speed);
            }
        } else {
            this.reached = true;
            --this.tryingTime;
        }
        if (this.reached) {
            digTick++;
            if (digTick >= 20) {
                System.out.println("Scuttler reached ash");
                if (!world.isClient) {
                    world.breakBlock(targetPos, false, this.mob, 1);
                    world.syncWorldEvent(2001, targetPos, 0);
                    ItemScatterer.spawn(world, targetPos.getX() + 0.5D, targetPos.getY() + 0.5D, targetPos.getZ() + 0.5D,
                            new ItemStack(Items.DIAMOND));
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
        BlockPos testPos;
        for (int range = 1; range <= maxRange; range++) {
            for (int x = -range; x <= range; x += range) {
                for (int DY = 1; DY <= maxDY; DY++) {
                    for (int y = -maxDY; y <= maxDY; y += maxDY) {
                        for (int z = -range; z <= range; z += range) {
                            testPos = startPos.add(x, y, z);
                            if (DigAshGoal.ASH_PREDICATE.test(world.getBlockState(testPos))) {
                                this.targetPos = testPos;
                                System.out.println("Found ash at: " + testPos);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Could not find ash");
        stop();
        return false;
    }
}
