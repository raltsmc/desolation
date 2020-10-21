package raltsmc.desolation.item;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AshItem extends ConfigurableFertilizerItem {
    public AshItem(Settings settings) {
        super(settings);
        setGrowChance(0.25);
        setGrowTries(1);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        Vec3d target = user.getPos()
                .add(new Vec3d (0, user.getEyeY() - user.getY(), 0).multiply(0.75))
                .add(user.getRotationVector().normalize().multiply(2));
        if (!world.isClient) {
            AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(world, target.x, target.y, target.z);
            areaEffectCloudEntity.setDuration(20);
            areaEffectCloudEntity.setParticleType(ParticleTypes.WHITE_ASH);
            areaEffectCloudEntity.setColor(0xcccccc);
            areaEffectCloudEntity.addEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 40, 1));
            areaEffectCloudEntity.setRadius(0.5F);
            areaEffectCloudEntity.setRadiusOnUse(0.5F);
            areaEffectCloudEntity.setRadiusGrowth(0.03F);
            areaEffectCloudEntity.setOwner(user);
            areaEffectCloudEntity.setWaitTime(0);
            areaEffectCloudEntity.playSound(SoundEvents.BLOCK_SNOW_BREAK, 1, 1);
            world.spawnEntity(areaEffectCloudEntity);
        }

        if (!user.abilities.creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
