package carpetfixes.mixins.optimizations.random;

import carpetfixes.CarpetFixesSettings;
import carpetfixes.helpers.XoroshiroCustomRandom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(targets = "net.minecraft.loot.context.LootContext$Builder")
public class LootContext$Builder_randomMixin {


    @Redirect(
            method = "random(J)Lnet/minecraft/loot/context/LootContext$Builder;",
            at = @At(
                    value = "NEW",
                    target = "java/util/Random"
            )
    )
    private static Random customRandom1(long seed) {
        return CarpetFixesSettings.optimizedRandom ? new XoroshiroCustomRandom(seed) : new Random(seed);
    }


    @Redirect(
            method = "random(JLjava/util/Random;)Lnet/minecraft/loot/context/LootContext$Builder;",
            at = @At(
                    value = "NEW",
                    target = "java/util/Random"
            )
    )
    private static Random customRandom2(long seed) {
        return CarpetFixesSettings.optimizedRandom ? new XoroshiroCustomRandom(seed) : new Random(seed);
    }


    @Redirect(
            method = "build(Lnet/minecraft/loot/context/LootContextType;)Lnet/minecraft/loot/context/LootContext;",
            at = @At(
                    value = "NEW",
                    target = "java/util/Random"
            )
    )
    private static Random customRandom3() {
        return CarpetFixesSettings.optimizedRandom ? new XoroshiroCustomRandom() : new Random();
    }
}
