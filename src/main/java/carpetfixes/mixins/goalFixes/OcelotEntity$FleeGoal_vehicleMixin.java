package carpetfixes.mixins.goalFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.passive.OcelotEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fixes ocelots trying to flee while in a vehicle. Just give up already!
 */

@Mixin(targets = "net/minecraft/entity/passive/OcelotEntity$FleeGoal")
public class OcelotEntity$FleeGoal_vehicleMixin {

    @Shadow
    @Final
    private OcelotEntity ocelot;


    @Inject(
            method = "canStart()Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cf$isNotInVehicle1(CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.ocelotsAndCatsTryToFleeInVehicleFix && this.ocelot.hasVehicle()) {
            cir.setReturnValue(false);
        }
    }


    @Inject(
            method = "shouldContinue()Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cf$isNotInVehicle2(CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.ocelotsAndCatsTryToFleeInVehicleFix && this.ocelot.hasVehicle()) {
            cir.setReturnValue(false);
        }
    }
}
