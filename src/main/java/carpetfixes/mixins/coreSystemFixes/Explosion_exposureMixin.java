package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * TNT Calculations are directional due to an offset in the calculation code.
 * This fix changes the exposure code to use the correct calculation.
 */

@Mixin(Explosion.class)
public class Explosion_exposureMixin {


    @Inject(
            method = "getExposure(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/entity/Entity;)F",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void cf$getExposure(Vec3d source, Entity entity, CallbackInfoReturnable<Float> cir) {
        if (CFSettings.incorrectExplosionExposureFix) {
            Box box = entity.getBoundingBox();
            double d = 1.0 / ((box.maxX - box.minX) * 2.0 + 1.0);
            double e = 1.0 / ((box.maxY - box.minY) * 2.0 + 1.0);
            double f = 1.0 / ((box.maxZ - box.minZ) * 2.0 + 1.0);
            double g = (1.0 - Math.floor(1.0 / d) * d) / 2.0;
            double h = (1.0 - Math.floor(1.0 / f) * f) / 2.0;
            if (!(d < 0.0) && !(e < 0.0) && !(f < 0.0)) {
                int i = 0, j = 0;
                for(double k = 0.0; k <= 1.0; k += d) {
                    for(double l = 0.0; l <= 1.0; l += e) {
                        for(double m = 0.0; m <= 1.0; m += f) {
                            double n = MathHelper.lerp(k + g, box.minX, box.maxX);
                            double o = MathHelper.lerp(l, box.minY, box.maxY);
                            double p = MathHelper.lerp(m + h, box.minZ, box.maxZ);
                            Vec3d vec3d = new Vec3d(n, o, p);
                            if (entity.getWorld().raycast(
                                    new RaycastContext(
                                            vec3d,
                                            source,
                                            RaycastContext.ShapeType.COLLIDER,
                                            RaycastContext.FluidHandling.NONE,
                                            entity
                                    )
                            ).getType() == HitResult.Type.MISS) {
                                ++i;
                            }
                            ++j;
                        }
                    }
                }
                cir.setReturnValue((float)i / (float)j);
            } else {
                cir.setReturnValue(0.0F);
            }
        }
    }
}
