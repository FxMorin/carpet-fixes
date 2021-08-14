package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CarpetFixesInit;
import carpetfixes.CarpetFixesSettings;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class MinecraftServer_updateSuppressionMixin {

    @Redirect(method = "tickWorlds", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;tick(Ljava/util/function/BooleanSupplier;)V"))
    private void fixUpdateSuppression(ServerWorld serverWorld, BooleanSupplier shouldKeepTicking){
        if(CarpetFixesSettings.updateSuppressionFix) {
            CarpetFixesInit.updateScheduler.get(serverWorld).tick();
        }
        serverWorld.tick(shouldKeepTicking);
    }
}
