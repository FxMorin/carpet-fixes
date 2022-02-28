package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CFSettings;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerWorld.class)
public abstract class ServerWorld_rainTimerMixin {

    /**
     * When players sleeps, the ThunderTime of the game gets reset. This means that it will take
     * much longer until there's thunder again. If you sleep every time there's rain, you will never see thunder.
     * This fix makes it so that the ThunderTime only gets reset when players sleep, and it's thundering.
     */


    @Shadow
    @Final
    private ServerWorldProperties worldProperties;


    @Redirect(
            method = "tick(Ljava/util/function/BooleanSupplier;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;resetWeather()V"
            )
    )
    public void onResetWeather(ServerWorld serverWorld) {
        this.worldProperties.setRainTime(0);
        this.worldProperties.setRaining(false);
        if (!CFSettings.sleepingResetsThunderFix || this.worldProperties.isThundering()) {
            this.worldProperties.setThunderTime(0); //Should thunder follow the same rules?
            this.worldProperties.setThundering(false); //I see this as annoying, does not have a bug report on it
        }
    }
}
