package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import carpetfixes.patches.TouchWaterGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.BeeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Fixes bees continuously touching the water and dying
 */

@Mixin(BeeEntity.class)
public class BeeEntity_swimGoalMixin {


    @ModifyConstant(
            method = "mobTick()V",
            constant = @Constant(intValue = 20)
    )
    protected int mobTick(int constant) {
        return CFSettings.beesSwimInWaterAndDieFix ? 40 : constant;
    }


    @Redirect(
            method = "initGoals()V",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/entity/ai/goal/SwimGoal"
            )
    )
    protected SwimGoal initGoals(MobEntity mob) {
        return CFSettings.beesSwimInWaterAndDieFix ? new TouchWaterGoal(mob) : new SwimGoal(mob);
    }
}
