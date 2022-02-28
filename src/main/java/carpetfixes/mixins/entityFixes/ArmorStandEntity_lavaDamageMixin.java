package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStandEntity.class)
public abstract class ArmorStandEntity_lavaDamageMixin {

    @Shadow
    protected abstract void updateHealth(DamageSource damageSource, float amount);


    @Inject(
            method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/damage/DamageSource;getSource()Lnet/minecraft/entity/Entity;",
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void beforeProjectileCheck(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.armorStandNegateLavaDamageFix && DamageSource.LAVA.equals(source)) {
            this.updateHealth(source, 4.0F);
            cir.setReturnValue(false);
        }
    }
}
