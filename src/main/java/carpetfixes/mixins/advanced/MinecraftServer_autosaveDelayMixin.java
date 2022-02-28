package carpetfixes.mixins.advanced;

import carpetfixes.CFSettings;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(MinecraftServer.class)
public class MinecraftServer_autosaveDelayMixin {


    @ModifyConstant(
            method = "tick(Ljava/util/function/BooleanSupplier;)V",
            constant = @Constant(intValue = 6000)
    )
    public int tickAutoSave(int autoSaveDelay) {
        return CFSettings.delayBetweenAutoSaves;
    }

    
    @ModifyConstant(
            method = "canExecute(Lnet/minecraft/server/ServerTask;)Z",
            constant = @Constant(intValue = 3) //statusUpdateDelay
    )
    public int shouldRunWithLatency(int maxTickLatency) {
        return CFSettings.maxTickLatency;
    }

    
    @ModifyConstant(
            method = "tick(Ljava/util/function/BooleanSupplier;)V",
            constant = @Constant(longValue = 5000000000L)
    )
    public long customStatusUpdateDelay(long maxTickLatency) {
        return CFSettings.statusUpdateDelay;
    }
}
