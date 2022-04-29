package carpetfixes.mixins.other;

import carpetfixes.helpers.DelayedWorldEventManager;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public class ServerWorld_tickWorldEventsMixin {

    private final ServerWorld self = (ServerWorld)(Object)this;


    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    public void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        DelayedWorldEventManager.tick(self);
    }
}
