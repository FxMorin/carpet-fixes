package carpetfixes.mixins.optimizations.random;

import carpetfixes.CFSettings;
import carpetfixes.helpers.XoroshiroCustomRandom;
import carpetfixes.settings.VersionPredicates;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
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

@Restriction(require = @Condition(value = "minecraft", versionPredicates = ">1.19-alpha.22.11.a <1.19-alpha.22.14.a"))
@Mixin(World.class)
public class World_randomMixin {

    @Mutable
    @Shadow
    @Final
    public Random random;


    @Inject(
            method = "<init>",
            require = 0,
            at = @At("TAIL")
    )
    private void CustomRandom(MutableWorldProperties properties, RegistryKey registryRef,
                              RegistryEntry registryEntry, Supplier profiler, boolean isClient,
                              boolean debugWorld, long seed, int i, CallbackInfo ci) {
        if (CFSettings.optimizedRandom) this.random = new XoroshiroCustomRandom();
    }
}

@Restriction(require = @Condition(value = "minecraft", versionPredicates = VersionPredicates.LT_22w12a))
@Mixin(World.class)
class World_oldRandomMixin {

    @Mutable
    @Shadow
    @Final
    public Random random;


    @SuppressWarnings("all")
    @Inject(
            method = "<init>",
            require = 0,
            at = @At("TAIL")
    )
    private void CustomRandom(MutableWorldProperties properties, RegistryKey<World> registryRef,
                              RegistryEntry<DimensionType> registryEntry, Supplier<Profiler> profiler,
                              boolean isClient, boolean debugWorld, long seed, CallbackInfo ci) {
        if (CFSettings.optimizedRandom) this.random = new XoroshiroCustomRandom();
    }
}
