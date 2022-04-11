package carpetfixes.mixins.reIntroduced;

import carpetfixes.CFSettings;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.BooleanSupplier;

@Mixin(ThreadedAnvilChunkStorage.class)
public abstract class ThreadedAnvilChunkStorage_oldSavingMixin {

    // Does not seem to be required anymore
    /*@Shadow
    private volatile Long2ObjectLinkedOpenHashMap<ChunkHolder> chunkHolders;

    @Shadow
    protected abstract boolean save(Chunk chunk);


    @Inject(
            method = "save(Z)V",
            at = @At("HEAD"),
            cancellable = true
    )
    protected void reIntroduceOldMechanics(boolean flush, CallbackInfo ci) {
        if (CFSettings.reIntroduceOnlyAutoSaveSaving && !flush) {
            this.chunkHolders.values().stream().filter(ChunkHolder::isAccessible).forEach(holder -> {
                Chunk chunk = holder.getSavingFuture().getNow(null);
                if (chunk instanceof ReadOnlyChunk || chunk instanceof WorldChunk) {
                    this.save(chunk);
                    holder.updateAccessibleStatus();
                }
            });
            ci.cancel();
        }
    }*/


    @Redirect(
            method = "unloadChunks(Ljava/util/function/BooleanSupplier;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/function/BooleanSupplier;getAsBoolean()Z"
            )
    )
    protected boolean dontUnloadRandomly(BooleanSupplier instance) {
        if (CFSettings.reIntroduceOnlyAutoSaveSaving) return false;
        return instance.getAsBoolean();
    }
}
