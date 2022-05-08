package carpetfixes.mixins.goalFixes.slowedGoalFix;

import carpetfixes.CFSettings;
import net.minecraft.entity.ai.goal.Goal;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "net/minecraft/entity/mob/EvokerEntity$SummonVexGoal")
public abstract class EvokerEntity$SummonVexGoal_slowMixin extends Goal {


    @Override
    public boolean shouldRunEveryTick() {
        return CFSettings.slowedEntityGoalsFix || super.shouldRunEveryTick();
    }
}
