package carpetfixes.mixins.featureFixes;

import carpetfixes.CFSettings;
import net.minecraft.structure.ShiftableStructurePiece;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.SwampHutGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Fixes swamp huts spawning the witch and cat in the same position
 */

@Mixin(SwampHutGenerator.class)
public abstract class SwampHutGenerator_samePosMixin extends ShiftableStructurePiece {

    protected SwampHutGenerator_samePosMixin(StructurePieceType type, int x, int y, int z, int width,
                                             int height, int depth, Direction orientation) {
        super(type, x, y, z, width, height, depth, orientation);
    }


    @Redirect(
            method = "spawnCat(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockBox;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/structure/SwampHutGenerator;" +
                            "offsetPos(III)Lnet/minecraft/util/math/BlockPos$Mutable;"
            )
    )
    private BlockPos.Mutable spawnCatOffset(SwampHutGenerator instance, int x, int y, int z) {
        return this.offsetPos(x + (CFSettings.witchAndCatSpawnMergedFix ? 1 : 0), y, z);
    }
}
