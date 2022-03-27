package carpetfixes.mixins.other;

import carpetfixes.helpers.EventManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftServer.class)
public class MinecraftServer_eventsMixin {


    @Inject(
            method = "reloadResources",
            at = @At("TAIL")
    )
    private void onReloadResources(Collection<String> dataPacks, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        cir.getReturnValue().handleAsync((value, throwable) -> {
            EventManager.onEvent(EventManager.CF_Event.DATAPACK_RELOAD);
            return value;
        }, (MinecraftServer) (Object) this);
    }
}
