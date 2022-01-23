package carpetfixes.mixins.optimizations.random;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.passive.SquidEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(SquidEntity.class)
public class SquidEntity_randomMixin {


    @Redirect(
            method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Random;setSeed(J)V"
            )
    )
    private void customRandom(Random instance, long seed) {
        if (!CarpetFixesSettings.entityRandomCrackingFix) {
            instance.setSeed(seed);
        }
    }
}
