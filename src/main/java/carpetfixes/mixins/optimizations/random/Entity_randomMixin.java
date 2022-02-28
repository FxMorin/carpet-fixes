package carpetfixes.mixins.optimizations.random;

import carpetfixes.CFSettings;
import carpetfixes.helpers.XoroshiroCustomRandom;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(value = Entity.class, priority = 1010)
public class Entity_randomMixin {

    private static Random rand = null; //Shared random instance for all entities


    @Redirect(
            method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V",
            require = 0,
            at = @At(
                    value = "NEW",
                    target = "java/util/Random"
            )
    )
    private Random CustomRandom() {
        if (!CFSettings.optimizedRandom) {
            if (CFSettings.entityRandomCrackingFix) return rand==null ? rand = new Random() : rand;
            return new Random();
        }
        if (CFSettings.entityRandomCrackingFix) return rand==null ? rand = new XoroshiroCustomRandom() : rand;
        return new XoroshiroCustomRandom();
    }
}

