package raltsmc.desolation.registry;

import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.world.structure.AshTinkerBaseGenerator;
import raltsmc.desolation.world.structure.AshTinkerBaseStructure;

public class DesolationStructures {
	public static final StructureType<AshTinkerBaseStructure> ASH_TINKER_BASE_STRUCTURE_TYPE = registerStructureType("ash_tinker_base", AshTinkerBaseStructure.CODEC);

	public static final StructurePieceType ASH_TINKER_BASE_PIECE = registerStructurePiece("ash_tinker_base", AshTinkerBaseGenerator::new);

	private static <S extends Structure> StructureType<S> registerStructureType(String name, Codec<S> codec) {
		return Registry.register(Registries.STRUCTURE_TYPE, Desolation.id(name), () -> codec);
	}

	private static StructurePieceType registerStructurePiece(String name, StructurePieceType piece) {
		return Registry.register(Registries.STRUCTURE_PIECE, Desolation.id(name), piece);
	}

	public static void init() { }
}
