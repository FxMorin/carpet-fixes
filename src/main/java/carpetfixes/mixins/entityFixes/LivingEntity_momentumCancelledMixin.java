package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Fixes velocity being cancelled per axis instead of combined. Resulting in weird behavior at very low velocities.
 * Although its pretty easy to notice on ice, and feels pretty broken
 */

@Mixin(LivingEntity.class)
public abstract class LivingEntity_momentumCancelledMixin extends Entity {

    public LivingEntity_momentumCancelledMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(
            method = "tickMovement",
            locals = LocalCapture.CAPTURE_FAILSOFT,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setVelocity(DDD)V",
                    shift = At.Shift.BEFORE
            )
    )
    private void cf$customVelocityCheck(CallbackInfo ci, Vec3d d, double h, double i, double j) {
        if (CFSettings.velocitySeparateAxisCancellingFix) {
            double x = d.x, y = d.y, z = d.z;
            if (Math.abs(x) + Math.abs(z) < 0.003D) {
                x = 0.0D;
                z = 0.0D;
            }
            if (Math.abs(y) < 0.003D) {
                y = 0.0D;
            }
            this.setVelocity(x, y, z);
        } else {
            this.setVelocity(h,i,j);
        }
    }


    @Redirect(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;setVelocity(DDD)V"
            )
    )
    private void cf$cancelSetVelocity(LivingEntity instance, double x, double y, double z) {}
}
