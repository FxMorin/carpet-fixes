package carpetfixes.mixins.featureFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.structure.BuriedTreasureGenerator;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.StructureSpawns;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.BuriedTreasureFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Random;

@Mixin(BuriedTreasureFeature.class)
public abstract class BuriedTreasureFeature_centerMixin extends StructureFeature {

    public BuriedTreasureFeature_centerMixin(RegistryEntryList<Biome> registryEntryList, Map<SpawnGroup,
            StructureSpawns> map, GenerationStep.Feature feature, boolean bl) {
        super(registryEntryList, map, feature, bl);
    }

    @Inject(
            method = "addPieces(Lnet/minecraft/structure/StructurePiecesCollector;" +
                    "Lnet/minecraft/world/gen/feature/StructureFeature$class_7149;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void customPiecePosition(StructurePiecesCollector collector,
                                            StructurePiecesGenerator.Context<ProbabilityConfig> context,
                                            CallbackInfo ci) {
        if (CFSettings.buriedTreasureAlwaysCenterFix) {
            ChunkPos chunkPos = context.chunkPos();
            Random rand = new Random(chunkPos.toLong());
            BlockPos blockPos = new BlockPos(
                    chunkPos.getOffsetX(rand.nextInt(16)),
                    90,
                    chunkPos.getOffsetZ(rand.nextInt(16))
            );
            collector.addPiece(new BuriedTreasureGenerator.Piece(blockPos));
            ci.cancel();
        }
    }
}
