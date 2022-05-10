package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.passive.WolfEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WolfEntity.class)
public class WolfEntity_snowballDamageMixin {


    @ModifyVariable(
            method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
            at = @At(
                    value = "LOAD",
                    ordinal = 0
            ),
            require = 0,
            name = "amount"
    )
    private float modifyAmount(float amount) {
        return CFSettings.zeroDamageHurtsWolvesFix && amount == 0.0F ? -1.0F : amount;
    }
}
