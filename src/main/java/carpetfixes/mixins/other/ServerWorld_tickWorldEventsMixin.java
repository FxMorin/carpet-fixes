package carpetfixes.mixins.other;

import carpetfixes.helpers.DelayedWorldEventManager;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

/**
 * Runs delayed world events at the end of the tick
 */

@Mixin(ServerWorld.class)
public class ServerWorld_tickWorldEventsMixin {


    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    private void cf$tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        DelayedWorldEventManager.tick((ServerWorld)(Object)this);
    }
}
