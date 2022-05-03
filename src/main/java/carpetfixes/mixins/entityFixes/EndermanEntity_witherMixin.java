package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EndermanEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EndermanEntity.class)
public class EndermanEntity_witherMixin {


    @Inject(
            method = "damage",
            locals = LocalCapture.CAPTURE_FAILSOFT,
            at = @At(
                    value = "RETURN",
                    ordinal = 1
            ),
            cancellable = true
    )
    private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir, boolean bl, int i) {
        if (CFSettings.endermanPainfulTeleportFix) cir.setReturnValue(bl);
    }
}
