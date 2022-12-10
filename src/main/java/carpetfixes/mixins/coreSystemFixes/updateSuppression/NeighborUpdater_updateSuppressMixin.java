package carpetfixes.mixins.coreSystemFixes.updateSuppression;

import carpetfixes.CFSettings;
import carpetfixes.helpers.UpdateSuppressionException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.block.NeighborUpdater;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Fix update suppression crashes by creating a new exception and catching it later
 */

@Mixin(NeighborUpdater.class)
public interface NeighborUpdater_updateSuppressMixin {


    @Inject(
            method = "tryNeighborUpdate(Lnet/minecraft/world/World;Lnet/minecraft/block/BlockState;" +
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;" +
                    "Lnet/minecraft/util/math/BlockPos;Z)V",
            locals = LocalCapture.CAPTURE_FAILSOFT,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/crash/CrashReport;create(Ljava/lang/Throwable;Ljava/lang/String;)" +
                            "Lnet/minecraft/util/crash/CrashReport;",
                    shift = At.Shift.BEFORE
            )
    )
    private static void skipCrashReport(World world, BlockState state, BlockPos pos,
                                        Block sourceBlock, BlockPos sourcePos, boolean notify,
                                        CallbackInfo ci, Throwable throwable) {
        if (CFSettings.updateSuppressionCrashFix)
            throw new UpdateSuppressionException("Update suppression");
    }
}
