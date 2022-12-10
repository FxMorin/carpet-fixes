package carpetfixes.mixins.featureFixes;

import carpetfixes.CFSettings;
import net.minecraft.structure.BuriedTreasureGenerator;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.structure.BuriedTreasureStructure;
import net.minecraft.world.gen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

/**
 * Buried treasure always generates in the center of the chunk, this can be easily fixed by simply settings the random
 * based on the chunk pos so that it's always the same.
 */

@Mixin(BuriedTreasureStructure.class)
public abstract class BuriedTreasureStructure_centerMixin extends Structure {

    protected BuriedTreasureStructure_centerMixin(Config arg) {
        super(arg);
    }

    @Inject(
            method = "addPieces(Lnet/minecraft/structure/StructurePiecesCollector;" +
                    "Lnet/minecraft/world/gen/structure/Structure$Context;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void customPiecePosition(StructurePiecesCollector collector,
                                            Context arg,
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
