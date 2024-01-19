package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CFSettings;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Chunk inhabited time does not get saved unless the chunk gets modified, so the values is nearly always incorrect.
 * This will make sure that all these chunks are when unloaded if they where not planned to be saved already
 */

@Mixin(Chunk.class)
public class Chunk_InhabitedTimeMixin {

    // This boolean value states if a chunk needs to be saved when shutting down, unloading, or auto-saving
    // Basically skip saving during every tick, and only do it during auto saves/unloading
    // TODO: When adding the api, make sure to allow other mods to use the needsFinalSaving option
    @Unique
    private volatile boolean cf$needsFinalSaving;

    @Shadow
    protected volatile boolean needsSaving;


    @Inject(
            method = "increaseInhabitedTime",
            at = @At("RETURN")
    )
    private void cf$increaseInhabitedTime(long l, CallbackInfo ci) {
        if (CFSettings.inhabitedTimeFix) {
            if (CFSettings.reIntroduceOnlyAutoSaveSaving) {
                this.needsSaving = true;
            } else {
                this.cf$needsFinalSaving = true;
            }
        }
    }


    @Inject(
            method = "setNeedsSaving",
            at = @At("RETURN")
    )
    private void cf$setNeedsSaving(boolean needsSaving, CallbackInfo ci) {
        if (!needsSaving) {
            this.cf$needsFinalSaving = false; // On saved, reset value
        }
    }


    @Inject(
            method = "needsSaving()Z",
            at = @At("RETURN"),
            cancellable = true
    )
    private void cf$needsSavingOrFinalSaving(CallbackInfoReturnable<Boolean> cir) {
        if (this.cf$needsFinalSaving && !cir.getReturnValue()) {
            cir.setReturnValue(!CFSettings.IS_TICK_SAVE.get());
        }
    }
}