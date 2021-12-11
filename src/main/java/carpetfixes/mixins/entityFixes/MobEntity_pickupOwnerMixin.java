package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MobEntity.class)
public class MobEntity_pickupOwnerMixin {

    @Redirect(
            method = "tickMovement()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/ItemEntity;isRemoved()Z"
            )
    )
    public boolean doesItemHaveOwnerTag(ItemEntity instance) {
        return instance.isRemoved() || CarpetFixesSettings.mobsIgnoreOwnerOnPickupFix && instance.getOwner() != null;
    }
}
