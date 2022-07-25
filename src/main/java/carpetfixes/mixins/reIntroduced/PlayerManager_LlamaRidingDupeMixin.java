package carpetfixes.mixins.reIntroduced;

import carpetfixes.CFSettings;
import carpetfixes.CarpetFixesServer;
import carpetfixes.helpers.Utils;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Function;

@Mixin(PlayerManager.class)
public abstract class PlayerManager_LlamaRidingDupeMixin {

    /**
     * Reimplements the dupe method where player1 can look into a Llama's inventory. Then player2 gets on the llama
     * and disconnects. Player1 can then take the items out of the llama's inventory.
     * Then when player2 logs back in, the items will still be in the llama's inventory. Duping it.
     */


    @Redirect(
            method = "onPlayerConnect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/EntityType;loadEntityWithPassengers(" +
                            "Lnet/minecraft/nbt/NbtCompound;Lnet/minecraft/world/World;" +
                            "Ljava/util/function/Function;)Lnet/minecraft/entity/Entity;"
            ),
            require = 0
    )
    private @Nullable Entity llamaReplaceOnConnect(NbtCompound nbt, World world,
                                                   Function<Entity, Entity> entityProcessor){
        if (CFSettings.reIntroduceDonkeyRidingDupe) Utils.reIntroduceDonkeyRidingDupe_replaceVehicle(nbt, world);
        return EntityType.loadEntityWithPassengers(nbt, world, entityProcessor);
    }


    @Redirect(
            method = "remove",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;hasVehicle()Z"
            )
    )
    private boolean llamaDupeOnRemove(ServerPlayerEntity serverPlayerEntity){
        if (serverPlayerEntity.hasVehicle()) {
            if(!CFSettings.reIntroduceDonkeyRidingDupe) return true;
            Entity entity = serverPlayerEntity.getRootVehicle();
            if (entity.hasPlayerRider()) {
                CarpetFixesServer.LOGGER.debug("[reIntroduceDonkeyRidingDupe] Removing player mount");
                serverPlayerEntity.stopRiding();
                entity.streamPassengersAndSelf().forEach((entityx) -> {
                    entityx.setRemoved(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
                });
            }
        }
        return false;
    }
}


@Restriction(require = @Condition("vmp"))
@Mixin(value = PlayerManager.class, priority = 1060)
abstract class PlayerManager_LlamaRidingDupeMixin_VMPCompat {


    @Dynamic
    @Redirect(
            method = "vmp$mountSavedVehicles",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/EntityType;loadEntityWithPassengers(" +
                            "Lnet/minecraft/nbt/NbtCompound;Lnet/minecraft/world/World;" +
                            "Ljava/util/function/Function;)Lnet/minecraft/entity/Entity;"
            ),
            require = 0
    )
    private @Nullable Entity llamaReplaceOnConnect(NbtCompound nbt, World world,
                                                   Function<Entity, Entity> entityProcessor){
        if (CFSettings.reIntroduceDonkeyRidingDupe) Utils.reIntroduceDonkeyRidingDupe_replaceVehicle(nbt, world);
        return EntityType.loadEntityWithPassengers(nbt, world, entityProcessor);
    }

}
