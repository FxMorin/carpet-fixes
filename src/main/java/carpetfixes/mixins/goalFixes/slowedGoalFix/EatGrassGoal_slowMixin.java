package carpetfixes.mixins.goalFixes.slowedGoalFix;

import carpetfixes.CFSettings;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.Goal;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EatGrassGoal.class)
public abstract class EatGrassGoal_slowMixin extends Goal {


    @Override
    public boolean shouldRunEveryTick() {
        return CFSettings.slowedEntityGoalsFix || super.shouldRunEveryTick();
    }
}
