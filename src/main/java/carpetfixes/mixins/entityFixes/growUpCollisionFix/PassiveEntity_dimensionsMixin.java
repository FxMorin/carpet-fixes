package carpetfixes.mixins.entityFixes.growUpCollisionFix;

import carpetfixes.CFSettings;
import carpetfixes.patches.EntityCalculateDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PassiveEntity.class)
public abstract class PassiveEntity_dimensionsMixin extends Entity {

    public PassiveEntity_dimensionsMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(
            method = "onTrackedDataSet(Lnet/minecraft/entity/data/TrackedData;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/passive/PassiveEntity;calculateDimensions()V",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    public void calculateCustomDimensions(TrackedData<?> data, CallbackInfo ci) {
        if (CFSettings.entityGrowingUpCollisionClippingFix) {
            ((EntityCalculateDimensions)this).calculateDimensionsWithoutHeight();
            super.onTrackedDataSet(data);
            ci.cancel();
        }
    }
}
