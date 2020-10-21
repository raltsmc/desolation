package raltsmc.desolation.registry;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.mixin.FoliagePlacerTypeInvoker;
import raltsmc.desolation.mixin.TrunkPlacerTypeInvoker;
import raltsmc.desolation.world.gen.foliage.CharredFoliagePlacer;
import raltsmc.desolation.world.gen.trunk.FallenTrunkPlacer;

public class DesolationFoliagePlacerTypes {
    public static FoliagePlacerType<CharredFoliagePlacer> CHARRED_FOLIAGE_PLACER;

    public static void init() {
        CHARRED_FOLIAGE_PLACER = register("charred_foliage_placer", CharredFoliagePlacer.CODEC);
    }

    private static <P extends FoliagePlacer> FoliagePlacerType<P> register(String name, Codec<P> codec) {
        return FoliagePlacerTypeInvoker.callRegister(Desolation.id(name).toString(), codec);
    }
}
