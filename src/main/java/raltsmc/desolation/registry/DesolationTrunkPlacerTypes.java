package raltsmc.desolation.registry;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.mixin.TrunkPlacerTypeInvoker;
import raltsmc.desolation.world.gen.trunk.FallenTrunkPlacer;
import raltsmc.desolation.world.gen.trunk.TwistedTrunkPlacer;

public class DesolationTrunkPlacerTypes {
    public static TrunkPlacerType<FallenTrunkPlacer> FALLEN;
    public static TrunkPlacerType<TwistedTrunkPlacer> TWISTED;

    public static void init() {
        FALLEN = register("fallen_trunk_placer", FallenTrunkPlacer.CODEC);
        TWISTED = register("twisted_trunk_placer", TwistedTrunkPlacer.CODEC);
    }

    private static <P extends TrunkPlacer> TrunkPlacerType<P> register(String name, Codec<P> codec) {
        return TrunkPlacerTypeInvoker.callRegister(Desolation.id(name).toString(), codec);
    }
}
