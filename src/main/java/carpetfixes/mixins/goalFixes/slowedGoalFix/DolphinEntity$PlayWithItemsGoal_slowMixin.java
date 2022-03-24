package carpetfixes.mixins.goalFixes.slowedGoalFix;

import carpetfixes.CFSettings;
import net.minecraft.entity.ai.goal.Goal;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "net/minecraft/entity/passive/DolphinEntity$PlayWithItemsGoal")
public abstract class DolphinEntity$PlayWithItemsGoal_slowMixin extends Goal {


    @Override
    public boolean shouldRunEveryTick() {
        return CFSettings.slowedEntityGoalsFix || super.shouldRunEveryTick();
    }
}
