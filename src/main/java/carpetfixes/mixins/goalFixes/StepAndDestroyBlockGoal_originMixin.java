package carpetfixes.mixins.goalFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.ai.goal.StepAndDestroyBlockGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fixes the step and destroy block goal using the default origin blockpos [0,0,0] as a valid first check without
 * proper checks for distance.
 */

@Mixin(StepAndDestroyBlockGoal.class)
public abstract class StepAndDestroyBlockGoal_originMixin extends MoveToTargetPosGoal {

    public StepAndDestroyBlockGoal_originMixin(PathAwareEntity mob, double speed, int range) {
        super(mob, speed, range);
    }

    @Inject(
            method = "hasAvailableTarget()Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onHasAvailableTarget(CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.stepAndDestroyBlockGoalUsesOriginFix) {
            cir.setReturnValue(
                    this.targetPos != null &&
                    this.mob.isInWalkTargetRange(this.targetPos) &&
                    this.isTargetPos(this.mob.world, this.targetPos) ||
                    this.findTargetPos()
            );
        }
    }
}
