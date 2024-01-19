package carpetfixes.mixins.playerFixes;

import carpetfixes.CFSettings;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes sweeping edge not applying fire aspect
 */

@Mixin(PlayerEntity.class)
public class PlayerEntity_sweepingFireMixin {


    @Inject(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;takeKnockback(DDD)V",
                    ordinal = 1,
                    shift = At.Shift.AFTER
            )
    )
    private void cf$onAttack(Entity target, CallbackInfo ci) {
        if (CFSettings.sweepingIgnoresFireAspectFix && target instanceof LivingEntity &&
                EnchantmentHelper.getFireAspect((PlayerEntity)(Object)this) > 0 && !target.isOnFire()) {
            target.setOnFireFor(1);
        }
    }
}
