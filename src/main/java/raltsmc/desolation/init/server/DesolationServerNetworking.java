package raltsmc.desolation.init.server;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import raltsmc.desolation.Desolation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DesolationServerNetworking {
    public static final Identifier PACKET_CINDER_SOUL_READY = Desolation.id("cinder_soul_ready");
    public static final Identifier PACKET_CINDER_SOUL_TICK = Desolation.id("cinder_soul_tick");
    public static final Identifier PACKET_CINDER_SOUL_DO_CINDER_DASH = Desolation.id("do_cinder_dash");

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(PACKET_CINDER_SOUL_TICK, (server, player, handler, buf, sender) -> server.execute(() -> {
            ServerWorld world = (ServerWorld) player.getWorld();
            Random random = ThreadLocalRandom.current();
            double d = player.getX() - 0.25D + random.nextDouble() / 2;
            double e = player.getY();
            double f = player.getZ() - 0.25D + random.nextDouble() / 2;

            double g = random.nextDouble() * 0.6D - 0.3D;
            double h = random.nextDouble() * 6.0D / 16.0D;
            double i = (random.nextDouble() - 0.5D) / 5.0D;

            world.spawnParticles(ParticleTypes.FLAME, d + g, e + h, f + g, 1, 0d, 0.1d + i, 0d, .1);
            if (random.nextDouble() < 0.25) {
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.AMBIENT, .8F, 1F);
            }
        }));

        ServerPlayNetworking.registerGlobalReceiver(PACKET_CINDER_SOUL_READY, (server, player, handler, buf, sender) -> server.execute(() -> {
            ServerWorld world = (ServerWorld) player.getWorld();
            Random random = ThreadLocalRandom.current();
            List<Vec3d> points = new ArrayList<>();

            double phi = Math.PI * (3. - Math.sqrt(5.));
            for (int i = 0; i <= 150; ++i) {
                double y = 1 - (i / (float) (250 - 1)) * 2;
                double radius = Math.sqrt(1 - y * y);
                double theta = phi * i;
                double x = Math.cos(theta) * radius;
                double z = Math.sin(theta) * radius;

                points.add(new Vec3d(player.getX() + x * 0.5, player.getY() + 1 + y, player.getZ() + z * 0.5));
            }

            for (Vec3d vec : points) {
                Vec3d vel = vec.subtract(player.getPos())
                        .normalize()
                        .multiply(0.12 + random.nextDouble() * 0.03)
                        .add(player.getVelocity().multiply(1, 0.1, 1))
                        .multiply(1.25, 1, 1.25);
                world.spawnParticles(ParticleTypes.FLAME, vec.x, vec.y, vec.z, 1, vel.x, vel.y, vel.z, .1);
            }
        }));

        ServerPlayNetworking.registerGlobalReceiver(PACKET_CINDER_SOUL_DO_CINDER_DASH, (server, player, handler, buf, sender) -> server.execute(() -> {
            ServerWorld world = (ServerWorld) player.getWorld();
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.PLAYERS, 1F, 1.6F);
        }));
    }
}
