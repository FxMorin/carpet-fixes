package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Fixes mobs being able to pickup items with owner tags
 */

@Mixin(MobEntity.class)
public class MobEntity_pickupOwnerMixin {


    @Redirect(
            method = "tickMovement()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/ItemEntity;isRemoved()Z"
            )
    )
    private boolean doesItemHaveOwnerTag(ItemEntity instance) {
        return instance.isRemoved() || CFSettings.mobsIgnoreOwnerOnPickupFix && instance.getOwner() != null;
    }
}
