package raltsmc.desolation.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;
import raltsmc.desolation.registry.DesolationBiomes;
import raltsmc.desolation.world.feature.DesolationConfiguredFeatures;
import raltsmc.desolation.world.feature.DesolationPlacedFeatures;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("UnstableApiUsage")
public class DesolationDynamicRegistryProvider extends FabricDynamicRegistryProvider {
	protected DesolationDynamicRegistryProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
		DesolationConfiguredFeatures.populate(entries);
		DesolationPlacedFeatures.populate(entries);
		DesolationBiomes.populate(entries);
	}

	@Override
	public String getName() {
		return "Desolation";
	}
}
