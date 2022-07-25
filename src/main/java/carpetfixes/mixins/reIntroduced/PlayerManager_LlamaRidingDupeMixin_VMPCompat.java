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

@Restriction(
        require = @Condition(type = Condition.Type.TESTER, tester = Utils.VMPConditionalPredicate.class)
)
@Mixin(value = PlayerManager.class, priority = 1060)
public abstract class PlayerManager_LlamaRidingDupeMixin_VMPCompat {

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
        if (CFSettings.reIntroduceDonkeyRidingDupe) {
            Utils.reIntroduceDonkeyRidingDupe_replaceVehicle(nbt, world);
        }
        return EntityType.loadEntityWithPassengers(nbt, world, entityProcessor);
    }

}
