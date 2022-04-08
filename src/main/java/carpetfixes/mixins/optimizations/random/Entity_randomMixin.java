package carpetfixes.mixins.optimizations.random;

import carpetfixes.CFSettings;
import carpetfixes.helpers.XoroshiroCustomRandom;
import carpetfixes.settings.VersionPredicates;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Restriction(require = @Condition(value = "minecraft", versionPredicates = VersionPredicates.LT_22w14a))
@Mixin(value = Entity.class, priority = 1010)
public class Entity_randomMixin {

    private static Random rand = null; //Shared random instance for all entities


    @SuppressWarnings("all")
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
            if (CFSettings.entityRandomCrackingFix) return rand == null ? rand = new Random() : rand;
            return new Random();
        }
        if (CFSettings.entityRandomCrackingFix) return rand == null ? rand = new XoroshiroCustomRandom() : rand;
        return new XoroshiroCustomRandom();
    }
}

