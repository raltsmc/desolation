package raltsmc.desolation.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class EmberBlock extends Block {
    public EmberBlock(Settings settings) { super(settings); }

    @Override
    public void onSteppedOn(World world, BlockPos pos, Entity entity) {
        if (!entity.isFireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            entity.damage(DamageSource.HOT_FLOOR, 1.0F);
            if (Math.random() > 0.9D) {
                entity.setFireTicks(120);
            }
        }

        super.onSteppedOn(world, pos, entity);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState blockstate, World world, BlockPos pos, Random random) {
        double d = (double)pos.getX() + 0.5D;
        double e = (double)pos.getY();
        double f = (double)pos.getZ() + 0.5D;

        double g = random.nextDouble() * 0.6D - 0.3D;
        double h = random.nextDouble() * 6.0D / 16.0D;
        double i = (random.nextDouble() - 0.5D) / 5.0D;

        world.addParticle(ParticleTypes.LARGE_SMOKE, d + g, e + h, f + g, 0.0D, 0.1D + i, 0.0D);
    }
}
