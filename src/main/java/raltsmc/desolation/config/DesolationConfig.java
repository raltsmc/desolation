package raltsmc.desolation.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.util.math.MathHelper;

@Config(name = "desolation")
public class DesolationConfig implements ConfigData {
    public boolean showGogglesOverlay = true;
    public boolean biomeSoundAmbience = true;

    @ConfigEntry.Category("generation") @ConfigEntry.Gui.RequiresRestart
    public double charredForestChance = 0.08D;
    @ConfigEntry.Category("generation") @ConfigEntry.Gui.RequiresRestart
    public double smallCharredForestChance = 0.04D;
    @ConfigEntry.Category("generation") @ConfigEntry.Gui.RequiresRestart
    public double charredForestClearingChance = 0.05D;
    @ConfigEntry.Category("generation") @ConfigEntry.Gui.RequiresRestart
    public boolean generateClearings = true;

    @Override
    public void validatePostLoad() {
        charredForestChance = MathHelper.clamp(charredForestChance, 0.01D, 1D);
        smallCharredForestChance = MathHelper.clamp(smallCharredForestChance, 0.01D, 1D);
        charredForestClearingChance = MathHelper.clamp(charredForestClearingChance, 0.01D, 1D);
    }
}
