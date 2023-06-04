package raltsmc.desolation.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class SparkParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;
    private final double windConstantX;
    private final double windConstantY;
    private final double windConstantZ;

    public SparkParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.velocityX = 0D;
        this.velocityY = random.nextDouble() * 0.2D + 0.1D;
        this.velocityZ = 0D;
        this.red = 1.0F;
        this.green = 0.5F;
        this.blue = 0.0F;
        this.gravityStrength = 0.25F;
        this.maxAge = 30;
        this.spriteProvider = spriteProvider;
        this.setSpriteForAge(spriteProvider);

        this.windConstantX = (Math.random() - 0.5D) * 2D;
        this.windConstantY = Math.random() - 0.5D;
        this.windConstantZ = (Math.random() - 0.5D) * 2D;
    }

    @Override
    public int getBrightness(float tint) {
        float f = ((float)this.age + tint) / (float)this.maxAge;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        int i = super.getBrightness(tint);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.velocityY -= 0.04D * (double)this.gravityStrength;
            this.move(this.velocityX, this.velocityY, this.velocityZ);

            long worldTime = this.world.getTime();
            double windFactorX = Math.sin(worldTime * Math.PI / 2500D) * 2D;
            double windFactorY = (Math.sin(worldTime * Math.PI / 30D) * 0.1) + 1.1D;
            double windFactorZ = Math.cos(worldTime * Math.PI / 3000D) * 2D;
            float toMax = this.age / (float)maxAge;
            this.velocityX += (toMax * windFactorX + this.windConstantX) * 0.01D;
            this.velocityY += (toMax * windFactorY + this.windConstantY) * 0.005D;
            this.velocityZ += (toMax * windFactorZ + this.windConstantZ) * 0.01D;

            this.green = (1F - toMax) * 0.5F;

            if (this.onGround) {
                this.velocityX *= 0.699999988079071D;
                this.velocityZ *= 0.699999988079071D;
            }

            this.setSpriteForAge(spriteProvider);
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(FabricSpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new SparkParticle(clientWorld, d, e, f, this.spriteProvider);
        }
    }
}
