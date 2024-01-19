package carpetfixes.mixins.playerFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes an exploit that allows you to stockpile infinite absorption hearts basically making you immortal
 */

@Mixin(PlayerEntity.class)
public abstract class PlayerEntity_absorptionMixin extends LivingEntity {

    protected PlayerEntity_absorptionMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public abstract float getAbsorptionAmount();


    @Inject(
            method = "setAbsorptionAmountUnclamped(F)V",
            at = @At("RETURN")
    )
    private void cf$onAbsorptionChanged(float amount, CallbackInfo ci) {
        if (CFSettings.absorptionStaysWithoutHeartsFix && this.getAbsorptionAmount() <= 0.0f &&
                this.hasStatusEffect(StatusEffects.ABSORPTION)) {
            this.removeStatusEffect(StatusEffects.ABSORPTION);
        }
    }
}
