package raltsmc.desolation.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = "desolation")
public class DesolationConfig implements ConfigData {
    public boolean showGogglesOverlay = true;
    // TODO test if this works client-side on servers (would be bad)
    public boolean inflictBiomeDebuffs = true;
    public boolean biomeSoundAmbience = true;
}
