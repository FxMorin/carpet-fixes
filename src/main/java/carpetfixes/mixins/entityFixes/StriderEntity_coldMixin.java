package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import carpetfixes.settings.VersionPredicates;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.entity.passive.StriderEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Restriction(require = @Condition(value = "minecraft", versionPredicates = VersionPredicates.LT_22w12a))
@Mixin(StriderEntity.class)
public class StriderEntity_coldMixin {


    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/passive/StriderEntity;setCold(Z)V"
            )
    )
    public void isNotCold(StriderEntity instance, boolean cold) {
        if (!CFSettings.noAIStriderGetsColdFix || !instance.isAiDisabled()) instance.setCold(cold);
    }
}
