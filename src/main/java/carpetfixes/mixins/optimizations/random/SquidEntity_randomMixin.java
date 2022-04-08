package carpetfixes.mixins.optimizations.random;

import carpetfixes.CFSettings;
import carpetfixes.settings.VersionPredicates;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.entity.passive.SquidEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Restriction(require = @Condition(value = "minecraft", versionPredicates = VersionPredicates.LT_22w14a))
@Mixin(value = SquidEntity.class, priority = 1010)
public class SquidEntity_randomMixin {


    @SuppressWarnings("all")
    @Redirect(
            method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V",
            require = 0,
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Random;setSeed(J)V"
            )
    )
    private void customRandom(Random instance, long seed) {
        if (!CFSettings.entityRandomCrackingFix) instance.setSeed(seed);
    }
}
