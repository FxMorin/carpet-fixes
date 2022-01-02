package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CarpetFixesSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.world.border.WorldBorder$StaticArea")
public class WorldBorder$StaticArea_collisionMixin {


    @Redirect(
            method = "recalculateBounds()V",
            at = @At(
                    value ="INVOKE",
                    target = "Ljava/lang/Math;floor(D)D"
            )
    )
    private double dontFloor(double a) {
        return CarpetFixesSettings.worldBorderCollisionRoundingFix ? a : Math.floor(a);
    }


    @Redirect(
            method = "recalculateBounds()V",
            at = @At(
                    value ="INVOKE",
                    target = "Ljava/lang/Math;ceil(D)D"
            )
    )
    private double dontCeil(double a) {
        return CarpetFixesSettings.worldBorderCollisionRoundingFix ? a : Math.ceil(a);
    }
}
