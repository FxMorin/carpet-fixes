package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CFSettings;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * If the player list is considered empty, the spawn chunks are no longer loaded. The fix is just to make sure
 * that the player list is never considered empty. This does the same as having a chunk loader on at all times.
 */

@Mixin(ServerWorld.class)
public class ServerWorld_spawnChunksMixin {

    @ModifyExpressionValue(
            method = "tick(Ljava/util/function/BooleanSupplier;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;isEmpty()Z"
            )
    )
    private boolean cf$spawnChunksStayLoaded(boolean isEmpty) {
        return !CFSettings.spawnChunkEntitiesUnloadingFix && isEmpty;
    }
}
