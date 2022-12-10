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

    private final PlayerEntity self = (PlayerEntity)(Object)this;


    @Inject(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;takeKnockback(DDD)V",
                    ordinal = 1,
                    shift = At.Shift.AFTER
            )
    )
    private void attack(Entity target, CallbackInfo ci) {
        if (CFSettings.sweepingIgnoresFireAspectFix && target instanceof LivingEntity &&
                EnchantmentHelper.getFireAspect(self) > 0 && !target.isOnFire())
            target.setOnFireFor(1);
    }
}
