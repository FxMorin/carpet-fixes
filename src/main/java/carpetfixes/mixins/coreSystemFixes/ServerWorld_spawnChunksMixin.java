package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(ServerWorld.class)
public class ServerWorld_spawnChunksMixin {

    /**
     * If the player list is considered empty, the spawn chunks are no longer
     * loaded. The fix is just to make sure that the player list is never
     * considered empty.
     */
    @Redirect(method= "tick(Ljava/util/function/BooleanSupplier;)V",require=0,at=@At(value="INVOKE",target="Ljava/util/List;isEmpty()Z"))
    public boolean spawnChunksStayLoaded(List list) {
        return !CarpetFixesSettings.spawnChunkEntitiesUnloadingFix && list.isEmpty();
    }
}
