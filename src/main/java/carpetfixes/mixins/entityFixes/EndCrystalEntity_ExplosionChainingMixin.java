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

    /**
     * Just bypass the isExplosionCheck, I can tell Mojang didn't want that much
     * recursion within the explosion which is why its not doing it. Although
     * they would need to implement an explosion list of entity ticking of sorts
     * which is too much for a fix to do.
     */
    @Inject(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;isExplosive()Z"),cancellable = true)
    public void isExplosiveBypass(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(CarpetFixesSettings.crystalExplodeOnExplodedFix || source.isExplosive());
    }
}
