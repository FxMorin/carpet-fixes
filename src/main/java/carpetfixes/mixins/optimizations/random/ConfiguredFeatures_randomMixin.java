package carpetfixes.mixins.optimizations.random;

import carpetfixes.CFSettings;
import carpetfixes.helpers.XoroshiroCustomRandom;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(value = ConfiguredFeatures.class, priority = 1010)
public class ConfiguredFeatures_randomMixin {


    @Redirect(
            method = "getDefaultConfiguredFeature()Lnet/minecraft/world/gen/feature/ConfiguredFeature;",
            require = 0,
            at = @At(
                    value = "NEW",
                    target = "java/util/Random"
            )
    )
    private static Random customRandom() {
        return CFSettings.optimizedRandom ? new XoroshiroCustomRandom() : new Random();
    }
}
