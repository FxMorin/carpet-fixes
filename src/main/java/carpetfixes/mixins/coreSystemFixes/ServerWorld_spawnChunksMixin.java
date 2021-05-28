package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(ServerWorld.class)
public class ServerWorld_spawnChunksMixin {

    @Redirect(method= "tick(Ljava/util/function/BooleanSupplier;)V",at=@At(value="INVOKE",target="Ljava/util/List;isEmpty()Z"))
    public boolean spawnChunksStayLoaded(List list) {
        return !CarpetFixesSettings.spawnChunkEntitiesUnloadingFix && list.isEmpty();
    }
}
