package raltsmc.desolation.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import net.minecraft.util.math.MathHelper;

@Config(name = "desolation")
public class DesolationConfig implements ConfigData {
    public boolean showGogglesOverlay = true;
    public boolean biomeSoundAmbience = true;

    @ConfigEntry.Category("generation") @ConfigEntry.Gui.RequiresRestart
    public double charredForestChance = 0.02D;
    @ConfigEntry.Category("generation") @ConfigEntry.Gui.RequiresRestart
    public double smallCharredForestChance = 0.01D;
    @ConfigEntry.Category("generation") @ConfigEntry.Gui.RequiresRestart
    public double charredForestClearingChance = 0.05D;
    @ConfigEntry.Category("generation") @ConfigEntry.Gui.RequiresRestart
    public boolean generateClearings = true;

    @Override
    public void validatePostLoad() throws ValidationException {
        charredForestChance = MathHelper.clamp(charredForestChance, 0.01D, 1D);
        smallCharredForestChance = MathHelper.clamp(smallCharredForestChance, 0.01D, 1D);
        charredForestClearingChance = MathHelper.clamp(charredForestClearingChance, 0.01D, 1D);
    }
}
