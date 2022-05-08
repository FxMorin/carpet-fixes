package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntity_potionEffectsMixin {

    @Shadow
    public abstract boolean isDead();


    @Inject(
            method = "isAffectedBySplashPotions",
            at = @At("HEAD"),
            cancellable = true
    )
    private void isAffectedBySplashPotions(CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.potionEffectsAffectDeadEntitiesFix && this.isDead()) cir.setReturnValue(false);
    }
}
