package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import carpetfixes.patches.VillagerEntityInteraction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityInteraction;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.UUID;

/**
 * Fixes villagers ignoring the fact that you healed them and saved there life.
 */

@Mixin(ZombieVillagerEntity.class)
public class ZombieVillagerEntity_offlinePlayerMixin {

    @Shadow
    private @Nullable UUID converter;


    @SuppressWarnings("all")
    @Inject(
            method = "finishConversion(Lnet/minecraft/server/world/ServerWorld;)V",
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;" +
                            "getPlayerByUuid(Ljava/util/UUID;)Lnet/minecraft/entity/player/PlayerEntity;",
                    shift = At.Shift.AFTER
            )
    )
    private void cf$allowHandlingWithoutPlayer(ServerWorld world, CallbackInfo ci, VillagerEntity villagerEntity,
                                               EquipmentSlot var3[], int var4, int var5) {
        if (CFSettings.villagerDiscountIgnoresOfflinePlayersFix) {
            ((VillagerEntityInteraction)villagerEntity).onInteractionWith(
                    EntityInteraction.ZOMBIE_VILLAGER_CURED,
                    this.converter
            );
        }
    }


    @Redirect(
            method = "finishConversion(Lnet/minecraft/server/world/ServerWorld;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;" +
                            "handleInteraction(Lnet/minecraft/entity/EntityInteraction;Lnet/minecraft/entity/Entity;" +
                            "Lnet/minecraft/entity/InteractionObserver;)V"
            )
    )
    private void cf$dontHandleInteraction(ServerWorld instance, EntityInteraction interaction,
                                          Entity entity, InteractionObserver observer) {
        if (!CFSettings.villagerDiscountIgnoresOfflinePlayersFix) {
            instance.handleInteraction(interaction, entity, observer);
        }
    }
}
