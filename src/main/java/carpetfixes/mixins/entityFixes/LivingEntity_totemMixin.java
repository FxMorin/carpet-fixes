package carpetfixes.mixins.entityFixes;

import carpetfixes.patches.EntityUsedTotem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * This class implements the EntityUsedTotem interface to correctly set the usedTotem value to calculate totem damage
 */

@Mixin(LivingEntity.class)
public class LivingEntity_totemMixin implements EntityUsedTotem {

    @Unique
    private boolean cf$usedTotem = false;

    @Override
    public boolean hasUsedTotem() {
        return cf$usedTotem;
    }


    @Inject(
            method = "tick()V",
            at = @At("HEAD")
    )
    private void cf$tickStart(CallbackInfo ci) {
        cf$usedTotem = false;
    }


    @Inject(
            method = "tryUseTotem(Lnet/minecraft/entity/damage/DamageSource;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setHealth(F)V"
            )
    )
    private void cf$hasUsedATotem(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        cf$usedTotem = true;
    }
}
