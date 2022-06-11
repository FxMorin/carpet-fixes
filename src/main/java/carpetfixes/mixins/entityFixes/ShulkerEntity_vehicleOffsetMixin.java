package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ShulkerEntity.class)
public abstract class ShulkerEntity_vehicleOffsetMixin extends Entity {

    public ShulkerEntity_vehicleOffsetMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(
            method = "getHeightOffset()D",
            locals = LocalCapture.CAPTURE_FAILSOFT,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;getType()Lnet/minecraft/entity/EntityType;",
                    shift = At.Shift.BY,
                    by = 2
            ),
            cancellable = true
    )
    private void modifyShulkerHeightForChestBoat(CallbackInfoReturnable<Double> cir, EntityType<?> type) {
        if (CFSettings.shulkersAreLowerInChestBoatFix && type == EntityType.CHEST_BOAT)
            cir.setReturnValue(0.1875 - this.getVehicle().getMountedHeightOffset());
    }
}
