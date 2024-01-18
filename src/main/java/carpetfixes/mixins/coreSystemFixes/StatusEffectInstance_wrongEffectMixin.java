package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CFSettings;
import carpetfixes.mixins.accessors.LivingEntityAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * This `update` method is currently only called in: `LivingEntity.tickStatusEffects()`
 * It calls onRemoved & onApplied for the same effect instance. Which for example the Absorption Effect will
 * remove absorption hearts based on the new effect.
 * Example:
 * 1. Eat an enchanted golden apple (Gives you absorption 3) - 8 hearts
 * 2. Eat a normal golden apple (Adds hidden effect of strength 1 to absorption) - 2 hearts
 * When the absorption runs out, instead of losing 6 hearts, you lose 2.
 * Allows you to stack absorption infinitely!
 *
 * An easier for mojang to do would actually be to just move the `overwriteCallback.run();` before the
 * `this.copyFrom(this.hiddenEffect);` call, and then it would work. Although if you ever have an effect which
 * does different things during onRemove & onApplied, you will run into issues which is why I split them up!
 */

@Mixin(StatusEffectInstance.class)
public abstract class StatusEffectInstance_wrongEffectMixin {

    @Shadow
    public abstract StatusEffect getEffectType();

    @Shadow
    public abstract int getAmplifier();


    @Inject(
            method = "update(Lnet/minecraft/entity/LivingEntity;Ljava/lang/Runnable;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/effect/StatusEffectInstance;" +
                            "copyFrom(Lnet/minecraft/entity/effect/StatusEffectInstance;)V",
                    shift = At.Shift.BEFORE
            )
    )
    private void addHere(LivingEntity entity, Runnable overwriteCallback, CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.brokenHiddenStatusEffectFix) {
            ((LivingEntityAccessor)entity).setEffectsChanged(true);
            this.getEffectType().onRemoved(entity.getAttributes());
        }
    }


    @Inject(
            method = "update(Lnet/minecraft/entity/LivingEntity;Ljava/lang/Runnable;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Runnable;run()V"
            )
    )
    private void removeHere(LivingEntity entity, Runnable overwriteCallback, CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.brokenHiddenStatusEffectFix) {
            this.getEffectType().onApplied(entity.getAttributes(), this.getAmplifier());
        }
    }


    @Redirect(
            method = "update(Lnet/minecraft/entity/LivingEntity;Ljava/lang/Runnable;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Runnable;run()V"
            )
    )
    private void dontUpdate(Runnable instance) {
        if (!CFSettings.brokenHiddenStatusEffectFix) instance.run();
    }
}
