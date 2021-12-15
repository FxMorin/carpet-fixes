package carpetfixes.mixins.advanced;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ThreadedAnvilChunkStorage.class)
public class ThreadedAnvilChunkStorage_chunkTimingsMixin {


    @ModifyConstant(
            method = "unloadChunks(Ljava/util/function/BooleanSupplier;)V",
            constant = @Constant(intValue = 20)
    )
    private static int modifyMaxChunksUnloadedPerTick(int original) {
        return CarpetFixesSettings.maxChunksSavedPerTick;
    }

    @ModifyConstant(
            method = "unloadChunks(Ljava/util/function/BooleanSupplier;)V",
            constant = @Constant(intValue = 200)
    )
    private static int modifyMaxChunksUnloadedPerAutoSave(int original) {
        return CarpetFixesSettings.maxChunksSavedPerAutoSave;
    }
}
