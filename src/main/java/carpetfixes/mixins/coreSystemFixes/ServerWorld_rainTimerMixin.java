package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CarpetFixesSettings;
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
     * When players sleep, the RainTime of the game gets reset. This means that it will take
     * much longer until it rains again. If you sleep every night, you will never see rain.
     * This fix makes it so that the RainTime only gets reset when players sleep and its
     * raining.
     */


    @Shadow @Final private ServerWorldProperties worldProperties;


    @Redirect(
            method= "tick(Ljava/util/function/BooleanSupplier;)V",
            at=@At(
                    value="INVOKE",
                    target="Lnet/minecraft/server/world/ServerWorld;resetWeather()V"
            ))
    public void onResetWeather(ServerWorld serverWorld) {
        if (!CarpetFixesSettings.sleepingResetsRainFix || this.worldProperties.isRaining()) {
            this.worldProperties.setRainTime(0);
            this.worldProperties.setRaining(false);
        }
        this.worldProperties.setThunderTime(0); //Should thunder follow the same rules?
        this.worldProperties.setThundering(false); //I see this as prob annoying also does not seem to have a bug report on it
    }
}
