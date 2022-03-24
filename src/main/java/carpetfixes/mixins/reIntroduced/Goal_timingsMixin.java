package carpetfixes.mixins.reIntroduced;

import carpetfixes.CFSettings;
import net.minecraft.entity.ai.goal.Goal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Goal.class)
public class Goal_timingsMixin {


    @Inject(
            method = "shouldRunEveryTick()Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void shouldRunEveryTickBypass(CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.reIntroduceProperGoalTimings) cir.setReturnValue(true);
    }


    @Inject(
            method = "toGoalTicks(I)I",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void toGoalTicksBypass(int ticks, CallbackInfoReturnable<Integer> cir) {
        if (CFSettings.reIntroduceProperGoalTimings) cir.setReturnValue(ticks);
    }
}
