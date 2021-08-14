package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CarpetFixesInit;
import carpetfixes.CarpetFixesSettings;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public class ServerWorld_updateSuppressionMixin {

    World self = (World)(Object)this;

    @Inject(method = "tick(Ljava/util/function/BooleanSupplier;)V", at = @At("HEAD"))
    public void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        if(CarpetFixesSettings.updateSuppressionFix) {
            CarpetFixesInit.updateScheduler.get(self).tick();
        }
    }
}
