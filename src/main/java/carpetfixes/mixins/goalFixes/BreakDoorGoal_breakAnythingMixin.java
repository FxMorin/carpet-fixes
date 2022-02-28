package carpetfixes.mixins.goalFixes;

import net.minecraft.block.DoorBlock;
import net.minecraft.entity.ai.goal.BreakDoorGoal;
import net.minecraft.entity.ai.goal.DoorInteractGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static carpetfixes.CFSettings.breakAnythingDoorGoalFix;

@Mixin(BreakDoorGoal.class)
public abstract class BreakDoorGoal_breakAnythingMixin extends DoorInteractGoal {

    public BreakDoorGoal_breakAnythingMixin(MobEntity mob) {
        super(mob);
    }

    @Shadow
    protected abstract boolean isDifficultySufficient(Difficulty difficulty);


    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/ai/goal/BreakDoorGoal;" +
                            "isDifficultySufficient(Lnet/minecraft/world/Difficulty;)Z"
            )
    )
    public boolean mightAlsoWannaCheckTheDoor_ItsNotThatHard(BreakDoorGoal breakDoorGoal, Difficulty difficulty) {
        return (!breakAnythingDoorGoalFix || DoorBlock.isWoodenDoor(this.mob.world, this.doorPos)) &&
                isDifficultySufficient(difficulty);
    }
}
