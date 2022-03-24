package carpetfixes.mixins.goalFixes.slowedGoalFix;

import carpetfixes.CFSettings;
import net.minecraft.entity.ai.goal.Goal;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "net/minecraft/entity/passive/PufferfishEntity$InflateGoal")
public abstract class PufferfishEntity$InflateGoal_slowMixin extends Goal {


    @Override
    public boolean shouldRunEveryTick() {
        return CFSettings.slowedEntityGoalsFix || super.shouldRunEveryTick();
    }
}
