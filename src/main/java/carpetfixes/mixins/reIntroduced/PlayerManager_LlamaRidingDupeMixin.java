package carpetfixes.mixins.reIntroduced;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(PlayerManager.class)
public abstract class PlayerManager_LlamaRidingDupeMixin {

    /**
     * Reimplements the dupe method where player1 can look into a Llama's inventory. Then player2 gets on the llama
     * and disconnects. Player1 can then take the items out of the llama's inventory.
     * Then when player2 logs back in, the items will still be in the llama's inventory. Duping it.
     */


    @ModifyArg(
            method = "onPlayerConnect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/EntityType;loadEntityWithPassengers(" +
                            "Lnet/minecraft/nbt/NbtCompound;Lnet/minecraft/world/World;" +
                            "Ljava/util/function/Function;)Lnet/minecraft/entity/Entity;"
            ),
            index = 2,
            require = 0
    )
    private Function<Entity, Entity> llamaReplaceOnConnect(NbtCompound nbt, World world,
                                                           Function<Entity, Entity> entityProcessor) {
        if (CFSettings.reIntroduceDonkeyRidingDupe) {
            return (vehicle) -> {
                Entity before = ((ServerWorld)world).getEntity(vehicle.getUuid());
                if (before != null) {
                    before.readNbt(vehicle.writeNbt(new NbtCompound()));
                    return before;
                }
                return !((ServerWorld)world).tryLoadEntity(vehicle) ? null : vehicle;
            };
        }
        return entityProcessor;
    }


    @Inject(
            method = "remove",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;getRootVehicle()" +
                            "Lnet/minecraft/entity/Entity;",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void llamaDupeOnRemove(ServerPlayerEntity player, CallbackInfo ci){
        if(CFSettings.reIntroduceDonkeyRidingDupe) ci.cancel();
    }
}
