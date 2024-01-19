package carpetfixes.mixins.goalFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.EnumSet;

/**
 * Fixes the follow parent goal breaking the movement controls, causing some funky behavior
 */

@Mixin(FollowParentGoal.class)
public abstract class FollowParentGoal_movementMixin extends Goal {

    // Thanks to Chumbanotz for doing the code analysis first


    @Inject(
            method = "canStart()Z",
            at = @At("RETURN")
    )
    private void cf$canStart(CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.followParentGoalBreaksMovementFix & cir.getReturnValue()) {
            this.setControls(EnumSet.of(Control.MOVE));
        }
    }
}
