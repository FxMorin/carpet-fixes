package carpetfixes.mixins.coreSystemFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Feature.class)
public class Feature_featureOrderMixin {
    @Inject(
            method = "register(Ljava/lang/String;Lnet/minecraft/world/gen/feature/Feature;)Lnet/minecraft/world/gen/feature/Feature;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static <C extends FeatureConfig, F extends Feature<C>> void registerOverride(String name, F feature, CallbackInfoReturnable<F> cir) {
        if (CarpetFixesSettings.worldgenIncorrectOrderFix && name.equals("lake")) {
            cir.setReturnValue((F) Registry.register(Registry.FEATURE, name, new NoOpFeature(DefaultFeatureConfig.CODEC)));
        }
    }
}
