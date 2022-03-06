package carpetfixes.mixins.other;

import net.minecraft.Bootstrap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Bootstrap.class)
public class Bootstrap_developmentMixin {


    @Redirect(
            method = "logMissing()V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/SharedConstants;isDevelopment:Z"
            )
    )
    private static boolean notIsDevelopment() {
        return false; //Carpet Crashes if this is ever true!
    }
}
