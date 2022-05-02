package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CFSettings;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Chunk.class)
public class Chunk_InhabitedTimeMixin {

    @Shadow
    protected volatile boolean needsSaving;

    @Inject(method = "increaseInhabitedTime", at = @At(value = "RETURN"))
    private void increaseInhabitedTime(long l, CallbackInfo ci) {
        if (CFSettings.inhabitedTimeFix) {
            needsSaving = true;
        }
    }

}