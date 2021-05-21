package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.EndCrystalEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndCrystalEntity.class)
public class EndCrystalEntity_ExplosionChainingMixin {

    @Inject(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;isExplosive()Z"),cancellable = true)
    public void isExplosiveBypass(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(CarpetFixesSettings.crystalExplodeOnExplodedFix || source.isExplosive());
    }
}
