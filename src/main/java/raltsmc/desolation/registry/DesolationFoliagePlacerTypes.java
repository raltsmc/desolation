package raltsmc.desolation.registry;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.mixin.world.gen.foliage.FoliagePlacerTypeInvoker;
import raltsmc.desolation.world.gen.foliage.CharredFoliagePlacer;

public class DesolationFoliagePlacerTypes {
    public static final FoliagePlacerType<CharredFoliagePlacer> CHARRED_FOLIAGE_PLACER = register("charred_foliage_placer", CharredFoliagePlacer.CODEC);

    private static <P extends FoliagePlacer> FoliagePlacerType<P> register(String name, Codec<P> codec) {
        return FoliagePlacerTypeInvoker.callRegister(Desolation.id(name).toString(), codec);
    }

    public static void init() { }
}
