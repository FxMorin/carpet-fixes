package carpetfixes.mixins.entityFixes.growUpCollisionFix;

import carpetfixes.CFSettings;
import carpetfixes.patches.ExtendedEntity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.PiglinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PiglinEntity.class)
public class PiglinEntity_dimensionsMixin {


    @Inject(
            method = "onTrackedDataSet(Lnet/minecraft/entity/data/TrackedData;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/mob/PiglinEntity;calculateDimensions()V",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    public void calculateCustomDimensions(TrackedData<?> data, CallbackInfo ci) {
        if (CFSettings.entityGrowingUpCollisionClippingFix) {
            ((ExtendedEntity)this).calculateDimensionsWithoutHeight();
            ci.cancel();
        }
    }
}
