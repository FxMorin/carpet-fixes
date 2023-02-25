package carpetfixes.mixins.coreSystemFixes.updateSuppression;

import carpet.utils.Messenger;
import carpetfixes.CFSettings;
import carpetfixes.helpers.UpdateSuppressionException;
import carpetfixes.mixins.accessors.MinecraftServerAccessor;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.crash.CrashException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

/**
 * Managing how exceptions are handled during ticks
 */

@Mixin(MinecraftServer.class)
public class MinecraftServer_crashFixMixin {

    private final MinecraftServer self = (MinecraftServer)(Object)this;
    private final MinecraftServerAccessor selfAccessor = (MinecraftServerAccessor)self;


    @Redirect(
            method = "tickWorlds",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;tick(Ljava/util/function/BooleanSupplier;)V"
            )
    )
    private void catchExceptions(ServerWorld serverWorld, BooleanSupplier shouldKeepTicking){
        if (!CFSettings.updateSuppressionCrashFix && !CFSettings.simulatedOutOfMemoryCrashFix) {
            serverWorld.tick(shouldKeepTicking);
            return;
        }
        try {
            serverWorld.tick(shouldKeepTicking);
        } catch (CrashException e) {
            Throwable cause = e.getCause();
            if (CFSettings.updateSuppressionCrashFix && cause instanceof UpdateSuppressionException) {
                logException("UpdateSuppression","world tick");
            } else if (CFSettings.simulatedOutOfMemoryCrashFix && cause instanceof OutOfMemoryError) {
                logException("OOM","world tick");
            } else {
                throw e;
            }
        } catch (UpdateSuppressionException e) {
            if (CFSettings.updateSuppressionCrashFix) {
                logException("UpdateSuppression","world tick");
            } else {
                throw e;
            }
        } catch (OutOfMemoryError e) {
            if (CFSettings.simulatedOutOfMemoryCrashFix) {
                logException("OOM", "world tick");
            } else {
                throw e;
            }
        }
    }


    @Inject(
            method = "runTasksTillTickEnd()V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void catchExceptionsDuringNetworking(CallbackInfo ci) {
        if (!CFSettings.simulatedOutOfMemoryCrashFix) return;
        ci.cancel();
        try {
            self.runTask();
            self.runTasks(() -> !selfAccessor.invokeShouldKeepTicking());
        } catch (CrashException e) {
            if (CFSettings.simulatedOutOfMemoryCrashFix && e.getCause() instanceof OutOfMemoryError) {
                logException("OOM","packets");
            } else {
                throw e;
            }
        } catch (OutOfMemoryError e) {
            if (CFSettings.simulatedOutOfMemoryCrashFix) {
                logException("OOM", "packets");
            } else {
                throw e;
            }
        }
    }

    private void logException(String source, String location) {
        Messenger.print_server_message(
                (MinecraftServer)(Object)this,
                source+") You just caused a server crash in "+location
        );
    }
}
