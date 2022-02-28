package carpetfixes.mixins.optimizations.random;

import carpetfixes.CFSettings;
import net.minecraft.entity.passive.SquidEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(value = SquidEntity.class, priority = 1010)
public class SquidEntity_randomMixin {


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
