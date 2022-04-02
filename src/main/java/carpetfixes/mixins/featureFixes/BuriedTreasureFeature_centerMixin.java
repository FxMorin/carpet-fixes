package carpetfixes.mixins.featureFixes;

import carpetfixes.CFSettings;
import net.minecraft.structure.BuriedTreasureGenerator;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.feature.BuriedTreasureFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(BuriedTreasureFeature.class)
public abstract class BuriedTreasureFeature_centerMixin extends StructureFeature {

    protected BuriedTreasureFeature_centerMixin(class_7302 arg) {
        super(arg);
    }

    @Inject(
            method = "addPieces(Lnet/minecraft/structure/StructurePiecesCollector;" +
                    "Lnet/minecraft/world/gen/feature/StructureFeature$class_7149;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void customPiecePosition(StructurePiecesCollector collector,
                                            StructureFeature.class_7149 arg,
                                            CallbackInfo ci) {
        if (CFSettings.buriedTreasureAlwaysCenterFix) {
            ChunkPos chunkPos = arg.chunkPos();
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
