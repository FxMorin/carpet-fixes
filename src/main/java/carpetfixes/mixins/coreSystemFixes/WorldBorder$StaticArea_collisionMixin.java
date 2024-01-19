package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CFSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Fix the world border collisions being incorrectly rounded, allowing entities to stand halfway in the border instead
 * of against it
 */

@Mixin(targets = "net.minecraft.world.border.WorldBorder$StaticArea")
public class WorldBorder$StaticArea_collisionMixin {


    @Redirect(
            method = "recalculateBounds()V",
            at = @At(
                    value ="INVOKE",
                    target = "Ljava/lang/Math;floor(D)D"
            )
    )
    private double cf$dontFloor(double a) {
        return CFSettings.worldBorderCollisionRoundingFix ? a : Math.floor(a);
    }


    @Redirect(
            method = "recalculateBounds()V",
            at = @At(
                    value ="INVOKE",
                    target = "Ljava/lang/Math;ceil(D)D"
            )
    )
    private double cf$dontCeil(double a) {
        return CFSettings.worldBorderCollisionRoundingFix ? a : Math.ceil(a);
    }
}
