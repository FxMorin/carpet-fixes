package carpetfixes.mixins.commandFixes;

import carpetfixes.CFSettings;
import net.minecraft.server.command.LocateCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fixes the LocateCommand distance calculation when the structure is more than 46340 blocks away.
 */

@Mixin(LocateCommand.class)
public class LocateCommand_distanceFixMixin {

    @Inject(at = @At("TAIL"), method = "getDistance", cancellable = true)
    private static void getDistance(int x1, int y1, int x2, int y2, CallbackInfoReturnable<Float> cir) {
        if (CFSettings.locateCommandDistanceFix) {
            double d = x2 - x1;
            double e = y2 - y1;
            cir.setReturnValue((float) Math.hypot(d, e));
        }
    }
}
