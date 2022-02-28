package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CFSettings;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(ServerWorld.class)
public class ServerWorld_spawnChunksMixin {

    /**
     * If the player list is considered empty, the spawn chunks are no longer loaded. The fix is just to make sure
     * that the player list is never considered empty. This does the same as having a chunk loader on at all times.
     */


    @Redirect(
            method = "tick(Ljava/util/function/BooleanSupplier;)V",
            require = 0,
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;isEmpty()Z"
            )
    )
    public boolean spawnChunksStayLoaded(List<ServerPlayerEntity> list) {
        return !CFSettings.spawnChunkEntitiesUnloadingFix && list.isEmpty();
    }
}
