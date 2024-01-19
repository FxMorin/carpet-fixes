package carpetfixes.mixins.advanced;

import carpetfixes.CFSettings;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ThreadedAnvilChunkStorage.class)
public class ThreadedAnvilChunkStorage_chunkTimingsMixin {


    @ModifyConstant(
            method = "unloadChunks(Ljava/util/function/BooleanSupplier;)V",
            constant = @Constant(intValue = 20)
    )
    private static int cf$modifyMaxChunksUnloadedPerTick(int original) {
        return CFSettings.maxChunksSavedPerTick;
    }

    
    @ModifyConstant(
            method = "unloadChunks(Ljava/util/function/BooleanSupplier;)V",
            constant = @Constant(intValue = 200)
    )
    private static int cf$modifyMaxChunksUnloadedPerAutoSave(int original) {
        return CFSettings.maxChunksSavedPerAutoSave;
    }

    
    @ModifyConstant(
            method = "save(Lnet/minecraft/server/world/ChunkHolder;)Z",
            constant = @Constant(longValue = 10000L)
    )
    private static long cf$modifyChunkSavingCooldown(long original) {
        return CFSettings.reIntroduceVeryAggressiveSaving ? -1L : (long)CFSettings.chunkSaveCooldownDelay;
    }

    
    @Redirect(
            method = "save(Lnet/minecraft/server/world/ChunkHolder;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/System;currentTimeMillis()J"
            )
    )
    private long cf$shouldRespectCooldown() {
        return CFSettings.reIntroduceVeryAggressiveSaving ? Long.MAX_VALUE : System.currentTimeMillis();
    }
}
