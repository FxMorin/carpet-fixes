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

@Mixin(Explosion.class)
public class Explosion_exposureMixin {

    /**
     * TNT Calculations are directional due to an offset in the calculation code.
     * This fix changes the exposure code to use the correct calculation.
     */


    @Inject(
            method= "getExposure(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/entity/Entity;)F",
            at=@At("HEAD"),
            cancellable = true
    )
    private static void getExposure(Vec3d source, Entity entity, CallbackInfoReturnable<Float> cir) {
        if (CFSettings.incorrectExplosionExposureFix) {
            Box box = entity.getBoundingBox();
            double d = 1.0D / ((box.maxX - box.minX) * 2.0D + 1.0D);
            double e = 1.0D / ((box.maxY - box.minY) * 2.0D + 1.0D);
            double f = 1.0D / ((box.maxZ - box.minZ) * 2.0D + 1.0D);
            double g = (1.0D - Math.floor(1.0D / d) * d) / 2.0D;
            double h = (1.0D - Math.floor(1.0D / f) * f) / 2.0D;
            if (!(d < 0.0D) && !(e < 0.0D) && !(f < 0.0D)) {
                int i = 0, j = 0;
                for(float k = 0.0F; k <= 1.0F; k = (float)((double)k + d)) {
                    for(float l = 0.0F; l <= 1.0F; l = (float)((double)l + e)) {
                        for(float m = 0.0F; m <= 1.0F; m = (float)((double)m + f)) {
                            double n = MathHelper.lerp((double)k + g, box.minX, box.maxX);
                            double o = MathHelper.lerp(l, box.minY, box.maxY);
                            double p = MathHelper.lerp((double)m + h, box.minZ, box.maxZ);
                            Vec3d vec3d = new Vec3d(n, o, p);
                            if (entity.world.raycast(
                                    new RaycastContext(
                                            vec3d,
                                            source,
                                            RaycastContext.ShapeType.COLLIDER,
                                            RaycastContext.FluidHandling.NONE,
                                            entity
                                    )
                            ).getType() == HitResult.Type.MISS) ++i;
                            ++j;
                        }
                    }
                }
                cir.setReturnValue((float)i / (float)j);
            }
            cir.setReturnValue(0.0F);
        }
    }
}
