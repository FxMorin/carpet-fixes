package carpetfixes.mixins.featureFixes;

import carpetfixes.CFSettings;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.gen.feature.GeodeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * The geode code looks for fluids around the amethysts and if it finds one it sets itself to waterlogged.
 * Completely ignoring the fact that the fluid may be lava and not water, resulting in interesting cobblestone
 * generators in geodes
 */

@Mixin(GeodeFeature.class)
public class GeodeFeature_lavaloggedMixin {


    @Redirect(
            method = "generate(Lnet/minecraft/world/gen/feature/util/FeatureContext;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/FluidState;isStill()Z"
            )
    )
    private boolean cf$isStillAndWater(FluidState state) {
        return CFSettings.geodeLavalogFix ? state.isEqualAndStill(Fluids.WATER) : state.isStill();
    }
}
