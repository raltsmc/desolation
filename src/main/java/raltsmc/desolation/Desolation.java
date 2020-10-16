package raltsmc.desolation;

import net.minecraft.util.Identifier;

public class Desolation {
    public static Identifier id(String path) {
        return new Identifier(DesolationMod.MODID, path);
    }
}
