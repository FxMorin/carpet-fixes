package carpetfixes.mixins.goalFixes;

import carpetfixes.CFSettings;
import carpetfixes.settings.VersionPredicates;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.entity.ai.goal.EscapeSunlightGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Restriction(require = {@Condition(value = "minecraft", versionPredicates = VersionPredicates.LT_22w12a)})
@Mixin(targets = "net/minecraft/entity/passive/FoxEntity$AvoidDaylightGoal")
public abstract class FoxEntity$AvoidDaylightGoal_originMixin extends EscapeSunlightGoal {

    public FoxEntity$AvoidDaylightGoal_originMixin(PathAwareEntity mob, double speed) {
        super(mob, speed);
    }


    @ModifyConstant(
            method = "canStart()Z",
            constant = @Constant(
                    intValue = 1,
                    ordinal = 0
            )
    )
    public int canModifyBoolean(int value) {
        return CFSettings.foxesGoToOriginDuringThunderFix ? (this.targetShadedPos() ? 1 : 0) : value;
    }
}
