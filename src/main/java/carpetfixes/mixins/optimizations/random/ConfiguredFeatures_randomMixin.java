package carpetfixes.mixins.optimizations.random;

import carpetfixes.CarpetFixesSettings;
import carpetfixes.helpers.XoroshiroCustomRandom;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(ConfiguredFeatures.class)
public class ConfiguredFeatures_randomMixin {


    @Redirect(
            method = "getDefaultConfiguredFeature()Lnet/minecraft/world/gen/feature/ConfiguredFeature;",
            at = @At(
                    value = "NEW",
                    target = "java/util/Random"
            )
    )
    private static Random customRandom() {
        return CarpetFixesSettings.optimizedRandom ? new XoroshiroCustomRandom() : new Random();
    }
}
