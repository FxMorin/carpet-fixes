package carpetfixes.mixins.other;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.Bootstrap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Bootstrap.class)
public class Bootstrap_developmentMixin {


    @ModifyExpressionValue(
            method = "logMissing()V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/SharedConstants;isDevelopment:Z"
            )
    )
    private static boolean notIsDevelopment(boolean original) {
        return false; //Carpet Crashes if this is ever true!
    }
}
