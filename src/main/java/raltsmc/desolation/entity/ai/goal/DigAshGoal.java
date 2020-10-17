package raltsmc.desolation.entity.ai.goal;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.WeightedList;
import net.minecraft.util.collection.WeightedPicker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import raltsmc.desolation.entity.AshScuttlerEntity;
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.registry.DesolationItems;

import java.util.EnumSet;
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
        BlockPos testPos;
        for (int range = 1; range <= maxRange; range++) {
            for (int x = -range; x <= range; x++) {
                for (int DY = 1; DY <= maxDY; DY++) {
                    for (int y = -DY; y <= DY; y++) {
                        for (int z = -range; z <= range; z++) {
                            testPos = startPos.add(x, y, z);
                            if (DigAshGoal.ASH_PREDICATE.test(world.getBlockState(testPos))) {
                                this.targetPos = testPos;
                                return true;
                            }
                        }
                    }
                }
            }
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
        ASH_LOOT_TABLE = new WeightedList<Item>()
                .add(Items.GLASS, 60)
                .add(Items.OAK_WOOD, 70)
                .add(Items.BONE, 58)
                .add(Items.EGG, 40)
                .add(Items.FLINT, 40)
                .add(Items.GUNPOWDER, 40)
                .add(Items.IRON_INGOT, 40)
                .add(Items.EMERALD, 20)
                .add(Items.LEATHER, 50)
                .add(Items.LEAD, 14)
                .add(Items.NAME_TAG, 14)
                .add(Items.SUGAR_CANE, 30)
                .add(Items.ENDER_PEARL, 16)
                .add(Items.FIRE_CHARGE, 10)
                .add(Items.DIAMOND, 5)
                .add(Items.TOTEM_OF_UNDYING, 2)
                .add(Items.ENCHANTED_GOLDEN_APPLE, 1);
    }
}
