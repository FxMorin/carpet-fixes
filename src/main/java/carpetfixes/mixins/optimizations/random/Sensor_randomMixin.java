package carpetfixes.mixins.optimizations.random;

import carpetfixes.CarpetFixesSettings;
import carpetfixes.helpers.XoroshiroCustomRandom;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(Sensor.class)
public class Sensor_randomMixin {


    @Redirect(
            method = "<clinit>()V",
            at = @At(
                    value = "NEW",
                    target = "java/util/Random"
            )
    )
    private static Random customRandom() {
        return CarpetFixesSettings.optimizedRandom ? new XoroshiroCustomRandom() : new Random();
    }
}
