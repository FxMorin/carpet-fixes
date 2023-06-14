package carpetfixes.mixins.playerFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes hunger going down in peaceful mode even though there is no hunger bar in peaceful
 */

@Mixin(PlayerEntity.class)
public abstract class PlayerEntity_hungerMixin extends LivingEntity {

    protected PlayerEntity_hungerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(
            method = "addExhaustion(F)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/HungerManager;addExhaustion(F)V",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void addExhaustion(float exhaustion, CallbackInfo ci) {
        if (CFSettings.hungerGoesDownInPeacefulFix && this.getWorld().getDifficulty() == Difficulty.PEACEFUL) ci.cancel();
    }
}
