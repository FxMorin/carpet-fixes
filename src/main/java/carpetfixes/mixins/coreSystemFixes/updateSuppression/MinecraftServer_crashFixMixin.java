package carpetfixes.mixins.coreSystemFixes.updateSuppression;

import carpet.utils.Messenger;
import carpetfixes.CFSettings;
import carpetfixes.helpers.UpdateSuppressionException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.crash.CrashException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class MinecraftServer_crashFixMixin {


    @Redirect(
            method = "tickWorlds",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;tick(Ljava/util/function/BooleanSupplier;)V"
            )
    )
    private void catchUpdateSuppression(ServerWorld serverWorld, BooleanSupplier shouldKeepTicking){
        if (!CFSettings.updateSuppressionCrashFix) {
            serverWorld.tick(shouldKeepTicking);
        } else {
            try {
                serverWorld.tick(shouldKeepTicking);
            } catch (CrashException e) {
                if (!(e.getCause() instanceof UpdateSuppressionException)) throw e;
                logUpdateSuppression();
            } catch (UpdateSuppressionException e) {
                logUpdateSuppression();
            }

        }
    }

    private void logUpdateSuppression() {
        Messenger.print_server_message((MinecraftServer)(Object)this, "You just caused a server crash in world tick");
    }
}
