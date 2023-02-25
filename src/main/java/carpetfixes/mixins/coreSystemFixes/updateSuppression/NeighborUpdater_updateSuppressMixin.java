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

/**
 * Fix update suppression crashes by creating a new exception and catching it later
 */

@Mixin(NeighborUpdater.class)
public interface NeighborUpdater_updateSuppressMixin {

    @Inject(
            method = "tryNeighborUpdate",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void catchNeighborUpdates(World world, BlockState state, BlockPos pos, Block sourceBlock,
                                             BlockPos sourcePos, boolean notify, CallbackInfo ci) {
        if (CFSettings.updateSuppressionCrashFix) {
            ci.cancel();
            try {
                state.neighborUpdate(world, pos, sourceBlock, sourcePos, notify);
            } catch (StackOverflowError | UpdateSuppressionException e) {
                throw new UpdateSuppressionException("Update suppression");
            }
        }
    }
}
