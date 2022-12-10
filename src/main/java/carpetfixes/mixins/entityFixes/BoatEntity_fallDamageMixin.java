package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes boats taking or not taking fall damage
 */

@Mixin(BoatEntity.class)
public abstract class BoatEntity_fallDamageMixin extends Entity {

    @Shadow
    private BoatEntity.Location location;

    public BoatEntity_fallDamageMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(
            method = "fall(DZLnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/vehicle/BoatEntity;" +
                            "handleFallDamage(FFLnet/minecraft/entity/damage/DamageSource;)Z",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void fall(double heightDifference, boolean onGround, BlockState landedState,
                      BlockPos landedPosition, CallbackInfo ci) {
        if (CFSettings.boatsTakeFallDamageFix) {
            this.fallDistance = 0.0F;
            ci.cancel();
        }
    }


    @Redirect(
            method = "fall(DZLnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)V",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/entity/vehicle/BoatEntity;location:" +
                            "Lnet/minecraft/entity/vehicle/BoatEntity$Location;"
            )
    )
    protected BoatEntity.Location fall(BoatEntity instance) {
        return CFSettings.boatsDontTakeFallDamageFix ? BoatEntity.Location.ON_LAND : this.location;
    }
}
