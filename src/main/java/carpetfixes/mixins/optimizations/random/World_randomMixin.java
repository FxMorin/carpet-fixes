package carpetfixes.mixins.optimizations.random;

import carpetfixes.CFSettings;
import carpetfixes.helpers.XoroshiroCustomRandom;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import java.util.function.Supplier;

@Mixin(World.class)
public class World_randomMixin {

    @Mutable @Shadow @Final public Random random;


    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void CustomRandom(MutableWorldProperties properties, RegistryKey registryRef, DimensionType dimensionType, Supplier profiler, boolean isClient, boolean debugWorld, long seed, CallbackInfo ci) {
        if (CFSettings.optimizedRandom) this.random = new XoroshiroCustomRandom();
    }
}
