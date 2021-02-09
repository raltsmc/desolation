package raltsmc.desolation.item;

import com.google.common.collect.ImmutableMultimap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import raltsmc.desolation.DesolationMod;
import raltsmc.desolation.entity.AshFlierEntity;
import raltsmc.desolation.registry.DesolationEntities;

import java.util.List;

public class ShrillWhistleItem extends Item {

    public ShrillWhistleItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.getItemCooldownManager().set(this, 60);
        user.incrementStat(Stats.USED.getOrCreateStat(this));

        /*if (world.isClient() && ClientPlayNetworking.canSend(DesolationMod.SHRILL_WHISTLE_PACKET_ID)) {
            ClientPlayNetworking.send(DesolationMod.SHRILL_WHISTLE_PACKET_ID, PacketByteBufs.empty());
        }*/
        /*if (!world.isClient()) {
            user.world.playSoundFromEntity((PlayerEntity) null, user.getX(), user.getY(), user.getZ(), DesolationMod.SHRILL_WHISTLE_SOUND_EVENT, SoundCategory.PLAYERS, 1F, RANDOM.nextFloat() * 0.1f + 1f);
            world.playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(), DesolationMod.SHRILL_WHISTLE_SOUND_EVENT, SoundCategory.PLAYERS, 1F, RANDOM.nextFloat() * 0.1f + 1f);
        }*/
        if (world.isClient()) {
            PositionedSoundInstance whistleSound = new PositionedSoundInstance(DesolationMod.SHRILL_WHISTLE_SOUND_EVENT, SoundCategory.PLAYERS, 4f, RANDOM.nextFloat() * 0.1f + 1f, user.getX(), user.getY(), user.getZ());
            MinecraftClient.getInstance().getSoundManager().play(whistleSound);
        } else {
            BlockPos pos = user.getBlockPos();
            List<AshFlierEntity> tamedFliers = user.world.getEntitiesByType(DesolationEntities.ASH_FLIER, Box.from(BlockBox.create(pos.getX() - 150, pos.getY() - 50, pos.getZ() - 150, pos.getX() + 150, pos.getY() + 50, pos.getZ() + 150)), (e) -> e.getOwner() == user);
            System.out.println(tamedFliers.size() + " tamed fliers found");
            for (AshFlierEntity flier : tamedFliers) {
                if (flier.isLanded()) {
                    flier.setLanded(false);
                }
                flier.setCalled(!flier.isCalled());
            }
        }

        return TypedActionResult.consume(itemStack);
    }
}
