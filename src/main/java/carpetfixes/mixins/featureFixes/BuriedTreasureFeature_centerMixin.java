package carpetfixes.mixins.featureFixes;

import carpetfixes.CFSettings;
import com.mojang.serialization.Codec;
import net.minecraft.structure.BuriedTreasureGenerator;
import net.minecraft.structure.StructureGeneratorFactory;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.BuriedTreasureFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(BuriedTreasureFeature.class)
public abstract class BuriedTreasureFeature_centerMixin extends StructureFeature<ProbabilityConfig> {

    public BuriedTreasureFeature_centerMixin(
            Codec<ProbabilityConfig> configCodec,
            StructureGeneratorFactory<ProbabilityConfig> piecesGenerator
    ) {
        super(configCodec, piecesGenerator);
    }

    @Inject(
            method = "addPieces(Lnet/minecraft/structure/StructurePiecesCollector;" +
                    "Lnet/minecraft/structure/StructurePiecesGenerator$Context;)V",
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

    @Inject(
            method = "getLocatedPos(Lnet/minecraft/util/math/ChunkPos;)Lnet/minecraft/util/math/BlockPos;",
            at = @At("HEAD"),
            cancellable = true
    )
    public void getLocatedPos(ChunkPos chunkPos, CallbackInfoReturnable<BlockPos> cir) {
        if (CFSettings.buriedTreasureAlwaysCenterFix) {
            Random rand = new Random(chunkPos.toLong());
            cir.setReturnValue(new BlockPos(
                    chunkPos.getOffsetX(rand.nextInt(16)),
                    0,
                    chunkPos.getOffsetZ(rand.nextInt(16)))
            );
        }
    }
}
