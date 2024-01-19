package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.decoration.ArmorStandEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStandEntity.class)
public abstract class ArmorStandEntity_damageMixin {

    @Shadow
    protected abstract void updateHealth(DamageSource damageSource, float amount);


    @Inject(
            method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/damage/DamageSource;getAttacker()Lnet/minecraft/entity/Entity;",
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void cf$beforeProjectileCheck(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.armorStandNegateLavaDamageFix && source.isOf(DamageTypes.LAVA)) {
            this.updateHealth(source, 4.0F);
            cir.setReturnValue(false);
        } else if (CFSettings.armorStandNegateCactusDamageFix && source.isOf(DamageTypes.CACTUS)) {
            this.updateHealth(source, amount);
            cir.setReturnValue(false);
        } else if (CFSettings.armorStandNegateAnvilDamageFix && source.isOf(DamageTypes.FALLING_BLOCK)) {
            this.updateHealth(source, amount * 3.0F);
            cir.setReturnValue(false);
        }
    }
}
