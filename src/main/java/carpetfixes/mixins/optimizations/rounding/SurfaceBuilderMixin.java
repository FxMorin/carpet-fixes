package carpetfixes.mixins.optimizations.rounding;

import carpetfixes.CarpetFixesSettings;
import carpetfixes.helpers.FastMath;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = SurfaceBuilder.class, priority = 1010)
public class SurfaceBuilderMixin {


    @Redirect(
            method = "getTerracottaBlock(III)Lnet/minecraft/block/BlockState;",
            require = 0,
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Math;round(D)J"
            )
    )
    private long fasterRound(double value) {
        return CarpetFixesSettings.optimizedRounding ? FastMath.round(value) : Math.round(value);
    }
}
