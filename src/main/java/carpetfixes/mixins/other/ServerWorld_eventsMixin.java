package carpetfixes.mixins.other;

import carpetfixes.CFSettings;
import carpetfixes.helpers.DelayedWorldEventManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public class ServerWorld_eventsMixin {

    /**
     * This ticks the UpdateScheduler & DelayedWorldEventManager
     */


    World self = (World)(Object)this;


    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    public void tickHEAD(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        CFSettings.updateScheduler.get(self).tick();
    }


    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    public void tickTAIL(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        DelayedWorldEventManager.tick(self);
    }
}
