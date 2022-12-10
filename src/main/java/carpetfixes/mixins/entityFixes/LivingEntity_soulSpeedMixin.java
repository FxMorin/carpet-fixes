package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes soul speed applying damage to your armor when riding a boat over soul sand/soil.
 */

@Mixin(LivingEntity.class)
public abstract class LivingEntity_soulSpeedMixin extends Entity {


    public LivingEntity_soulSpeedMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(
            method = "addSoulSpeedBoostIfNeeded()V",
            at = @At(
                    shift = At.Shift.AFTER,
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/attribute/EntityAttributeInstance;" +
                            "addTemporaryModifier(Lnet/minecraft/entity/attribute/EntityAttributeModifier;)V"
            ),
            cancellable = true
    )
    private void dontDamageIfRidingEntity(CallbackInfo ci) {
        if (CFSettings.soulSpeedIncorrectDamageFix && this.hasVehicle()) ci.cancel();
    }
}
