package raltsmc.desolation.registry;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import raltsmc.desolation.Desolation;

public class DesolationSounds {
    public static SoundEvent MUSIC_DISC_ASHES_SOUND;
    public static SoundEvent EMBER_BLOCK_POP_1;
    public static SoundEvent EMBER_BLOCK_POP_2;
    public static SoundEvent EMBER_BLOCK_POP_3;
    public static SoundEvent EMBER_BLOCK_POP_4;

    public static void init() {
        MUSIC_DISC_ASHES_SOUND = register("music_disc.ashes");
        EMBER_BLOCK_POP_1 = register("block.ember_pop1");
        EMBER_BLOCK_POP_2 = register("block.ember_pop2");
        EMBER_BLOCK_POP_3 = register("block.ember_pop3");
        EMBER_BLOCK_POP_4 = register("block.ember_pop4");
    }

    public static SoundEvent register(String name) {
        Identifier id = Desolation.id(name);
        SoundEvent event = new SoundEvent(id);
        Registry.register(Registry.SOUND_EVENT, id, event);
        return event;
    }
}
