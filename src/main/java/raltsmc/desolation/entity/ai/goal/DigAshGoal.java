package raltsmc.desolation.entity.ai.goal;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.entity.AshScuttlerEntity;
import raltsmc.desolation.registry.DesolationBlocks;
import raltsmc.desolation.registry.DesolationItems;

import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Predicate;

public class DigAshGoal extends MoveToTargetPosGoal {
    private static final Predicate<BlockState> ASH_PREDICATE;
    private static final long DIG_DURATION_TICKS = 20;
    private final AshScuttlerEntity mob;
    private final World world;
    private final int range;
    private final int maxDY;
    private int digTick;

    public DigAshGoal(AshScuttlerEntity mob, double speed, int range, int maxDY) {
        super(mob, speed, range, maxDY);
        this.mob = mob;
        this.world = mob.getWorld();
        this.range = range;
        this.maxDY = maxDY;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK, Control.JUMP));
    }

    public boolean canStart() {
        return mob.isSearching() && this.getNearestBlock(mob.getBlockPos(), range, maxDY);
    }

    public void start() {
        super.start();
        digTick = 0;
    }

    public void stop() {
        super.stop();
        mob.setSearching(false);
    }

    public void tick() {
        Vec3d location = targetPos.toCenterPos();

        if (!location.isInRange(mob.getPos(), getDesiredDistanceToTarget())) {
            ++tryingTime;
            Vec3d vel = mob.getVelocity();
            if (this.shouldResetPath() && new Vec3d(vel.x, 0, vel.z).lengthSquared() < 0.2f) {
                mob.setVelocity(vel.x, 0.5f, vel.z);
                mob.getNavigation().startMovingTo(location.getX(), location.getY(), location.getZ(), speed);
            }
        } else {
            --tryingTime;
            if (++digTick >= DIG_DURATION_TICKS) {
                if (!world.isClient) {
                    assert world.getServer() != null;
                    Identifier id = Desolation.id("misc/ash_scuttler_dig");

                    world.breakBlock(targetPos, false, mob, 1);
                    world.syncWorldEvent(2001, targetPos, 0);

                    LootTable lootTable = world.getServer().getLootManager().getLootTable(id);
                    LootContextParameterSet parameterSet = new LootContextParameterSet.Builder((ServerWorld) world)
                            .add(LootContextParameters.ORIGIN, location)
                            .add(LootContextParameters.THIS_ENTITY, mob)
                            .build(LootContextTypes.GIFT);

                    ObjectArrayList<ItemStack> list = lootTable.generateLoot(parameterSet);
                    for (ItemStack itemStack : list) {
                        ItemEntity itemEntity = new ItemEntity(world, location.getX(), location.getY(), location.getZ(), itemStack);
                        itemEntity.setToDefaultPickupDelay();
                        world.spawnEntity(itemEntity);
                    }
                }
                stop();
            }
        }
    }

    public double getDesiredDistanceToTarget() {
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
