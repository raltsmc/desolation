package raltsmc.desolation.registry;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.mixin.world.gen.trunk.TrunkPlacerTypeInvoker;
import raltsmc.desolation.world.gen.trunk.BasedTrunkPlacer;
import raltsmc.desolation.world.gen.trunk.FallenTrunkPlacer;

public class DesolationTrunkPlacerTypes {
    public static TrunkPlacerType<FallenTrunkPlacer> FALLEN;
    public static TrunkPlacerType<BasedTrunkPlacer> BASED;

    public static void init() {
        FALLEN = register("fallen_trunk_placer", FallenTrunkPlacer.CODEC);
        BASED = register("based_trunk_placer", BasedTrunkPlacer.CODEC);
    }

    private static <P extends TrunkPlacer> TrunkPlacerType<P> register(String name, Codec<P> codec) {
        return TrunkPlacerTypeInvoker.callRegister(Desolation.id(name).toString(), codec);
    }
}
