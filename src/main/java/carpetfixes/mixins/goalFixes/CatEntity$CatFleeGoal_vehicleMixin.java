package carpetfixes.mixins.goalFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.passive.CatEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fixes cats trying to flee while in a vehicle. Just give up already!
 */

@Mixin(targets = "net/minecraft/entity/passive/CatEntity$CatFleeGoal")
public class CatEntity$CatFleeGoal_vehicleMixin {

    @Shadow
    @Final
    private CatEntity cat;


    @Inject(
            method = "canStart()Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cf$isNotInVehicle1(CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.ocelotsAndCatsTryToFleeInVehicleFix && this.cat.hasVehicle()) {
            cir.setReturnValue(false);
        }
    }


    @Inject(
            method = "shouldContinue()Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cf$isNotInVehicle2(CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.ocelotsAndCatsTryToFleeInVehicleFix && this.cat.hasVehicle()) {
            cir.setReturnValue(false);
        }
    }
}
