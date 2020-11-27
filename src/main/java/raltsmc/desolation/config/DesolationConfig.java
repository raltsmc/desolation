package raltsmc.desolation.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = "desolation")
public class DesolationConfig implements ConfigData {
    //@ConfigEntry.Gui.RequiresRestart
    public boolean showGogglesOverlay = true;
}
