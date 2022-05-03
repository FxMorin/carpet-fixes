package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndermanEntity.class)
public abstract class EndermanEntity_vehicleMixin extends LivingEntity {

    protected EndermanEntity_vehicleMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/damage/DamageSource;getSource()Lnet/minecraft/entity/Entity;",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void damageIfNotRidingInStyle(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.endermanAvoidProjectilesInVehicleFix && this.hasVehicle())
            cir.setReturnValue(super.damage(source, amount));
    }


    @Inject(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/mob/HostileEntity;" +
                            "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void skipTeleportWhenRiding(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.endermanAvoidProjectilesInVehicleFix && this.hasVehicle())
            cir.setReturnValue(super.damage(source, amount));
    }
}
