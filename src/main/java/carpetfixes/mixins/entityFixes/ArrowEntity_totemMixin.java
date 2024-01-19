package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import carpetfixes.patches.EntityUsedTotem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ArrowEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Fix instant damage arrows being able to bypass totems
 */

@Mixin(ArrowEntity.class)
public abstract class ArrowEntity_totemMixin {


    @Redirect(
            method = "onHit",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;addStatusEffect(" +
                            "Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z"
            )
    )
    private boolean cf$onHit(LivingEntity instance, StatusEffectInstance effect, Entity source) {
        if (CFSettings.arrowEffectsBypassTotemsFix &&
                effect.getEffectType() == StatusEffects.INSTANT_DAMAGE &&
                ((EntityUsedTotem)instance).hasUsedTotem()
        ) {
            return false;
        }
        return instance.addStatusEffect(effect,source);
    }
}
