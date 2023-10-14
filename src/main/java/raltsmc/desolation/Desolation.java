package raltsmc.desolation;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import raltsmc.desolation.config.DesolationConfig;
import raltsmc.desolation.init.server.DesolationServerNetworking;
import raltsmc.desolation.registry.DesolationRegistries;
import raltsmc.desolation.world.gen.world.DesolationBiolithGeneration;
import software.bernie.geckolib.GeckoLib;

public class Desolation implements ModInitializer {

	public static final String MOD_ID = "desolation";
	public static final Logger LOGGER = LogManager.getLogger(StringUtils.capitalize(MOD_ID));
	public static final DesolationConfig CONFIG = AutoConfig.register(DesolationConfig.class, JanksonConfigSerializer::new).getConfig();

	@Override
	public void onInitialize() {
		DesolationRegistries.init();
		DesolationBiolithGeneration.init();
		GeckoLib.initialize();
		DesolationServerNetworking.init();

		LOGGER.info("DesolationX initialized!");
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}
