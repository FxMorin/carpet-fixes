package carpetfixes.mixins.goalFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.passive.TameableEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fixes the Sit goal continuously resetting when the owner is offline
 */

@Mixin(SitGoal.class)
public class SitGoal_goalLoopMixin {

    @Shadow
    @Final
    private TameableEntity tameable;


    @Inject(
            method = "shouldContinue",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cf$properShouldContinue(CallbackInfoReturnable<Boolean> cir) {
        if (!CFSettings.sitGoalAlwaysResettingFix) {
            return;
        }
        if (!this.tameable.isTamed() || this.tameable.isInsideWaterOrBubbleColumn() || !this.tameable.isOnGround()) {
            cir.setReturnValue(false);
        } else if (this.tameable.getOwner() == null) {
            cir.setReturnValue(true);
        }
    }
}
