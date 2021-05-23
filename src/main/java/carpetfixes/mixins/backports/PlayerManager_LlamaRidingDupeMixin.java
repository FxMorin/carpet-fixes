package carpetfixes.mixins.backports;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.Entity;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import java.util.Iterator;
import org.apache.logging.log4j.Logger;

@Mixin(PlayerManager.class)
public abstract class PlayerManager_LlamaRidingDupeMixin {

    @Final
    @Shadow private static final Logger LOGGER = LogManager.getLogger();

    @Redirect(method = "remove", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;hasVehicle()Z"))
    private boolean llamaDupe(ServerPlayerEntity serverPlayerEntity){
        if (serverPlayerEntity.hasVehicle()) {
            if(CarpetFixesSettings.donkeyRidingDupeFix)
                return true;
            Entity entity = serverPlayerEntity.getRootVehicle();
            if (entity.hasPlayerRider()) {
                LOGGER.debug("Removing player mount");
                serverPlayerEntity.stopRiding();
                serverPlayerEntity.getServerWorld().removeEntity(entity);
                entity.removed = false;
                Entity entity2;
                for(Iterator var4 = entity.getPassengersDeep().iterator(); var4.hasNext(); entity2.removed = false) {
                    entity2 = (Entity)var4.next();
                    serverPlayerEntity.getServerWorld().removeEntity(entity2);
                }
                serverPlayerEntity.getServerWorld().getChunk(serverPlayerEntity.chunkX, serverPlayerEntity.chunkZ).markDirty();
            }
        }
        return false;
    }
}
