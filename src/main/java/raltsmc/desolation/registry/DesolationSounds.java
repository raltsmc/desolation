package raltsmc.desolation.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import raltsmc.desolation.Desolation;

public class DesolationSounds {
    public static final SoundEvent MUSIC_DISC_ASHES_SOUND = register("music_disc.ashes");
    public static final SoundEvent EMBER_BLOCK_POP_1 = register("block.ember_pop1");
    public static final SoundEvent EMBER_BLOCK_POP_2 = register("block.ember_pop2");
    public static final SoundEvent EMBER_BLOCK_POP_3 = register("block.ember_pop3");
    public static final SoundEvent EMBER_BLOCK_POP_4 = register("block.ember_pop4");

    public static SoundEvent register(String name) {
        Identifier id = Desolation.id(name);
        SoundEvent event = SoundEvent.of(id);
        Registry.register(Registries.SOUND_EVENT, id, event);
        return event;
    }

    public static void init() { }
}
