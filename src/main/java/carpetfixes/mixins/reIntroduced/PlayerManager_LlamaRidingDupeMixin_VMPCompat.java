package carpetfixes.mixins.reIntroduced;

import carpetfixes.CFSettings;
import carpetfixes.helpers.Utils;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Restriction(
        require = @Condition(type = Condition.Type.TESTER, tester = Utils.VMPConditionalPredicate.class)
)
@Mixin(value = PlayerManager.class, priority = 1060)
public abstract class PlayerManager_LlamaRidingDupeMixin_VMPCompat {

    /**
     * Reimplements the dupe method where player1 can look into a Llama's inventory. Then player2 gets on the llama
     * and disconnects. Player1 can then take the items out of the llama's inventory.
     * Then when player2 logs back in, the items will still be in the llama's inventory. Duping it.
     */


    @Dynamic
    @ModifyArg(
            method = {"vmp$mountSavedVehicles", "c2me$mountSavedVehicles"},
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
            return Utils.reIntroduceDonkeyRidingDupe_entityFetcherFunction((ServerWorld) world);
        }
        return entityProcessor;
    }

}
