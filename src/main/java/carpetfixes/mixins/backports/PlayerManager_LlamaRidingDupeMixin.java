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

    /**
     * Reimplements the dupe method where player1 can look into a Llama's inventory. Then player2 gets
     * on the llama and disconnects. Player1 can then take the items out of the llama's inventory.
     * Then when player2 logs back in, the items will still be in the llama's inventory. Duping it.
     */


    @Final @Shadow private static final Logger LOGGER = LogManager.getLogger();


    @Redirect(
            method = "remove",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;hasVehicle()Z"
            ))
    private boolean llamaDupe(ServerPlayerEntity serverPlayerEntity){
        if (serverPlayerEntity.hasVehicle()) {
            if(!CarpetFixesSettings.oldDonkeyRidingDupe)
                return true;
            Entity entity = serverPlayerEntity.getRootVehicle();
            if (entity.hasPlayerRider()) {
                LOGGER.debug("Removing player mount");
                serverPlayerEntity.stopRiding();
                Entity entity2;
                for(Iterator var4 = entity.getPassengersDeep().iterator(); var4.hasNext();) {
                    entity2 = (Entity)var4.next();
                    entity2.discard();
                }
                serverPlayerEntity.getServerWorld().getChunk(serverPlayerEntity.getChunkPos().x, serverPlayerEntity.getChunkPos().z).markDirty();
            }
        }
        return false;
    }
}
