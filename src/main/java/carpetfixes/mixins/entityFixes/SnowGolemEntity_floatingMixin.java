package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes snow golems creating snow on the ground when they are not touching the ground
 */

@Mixin(SnowGolemEntity.class)
public abstract class SnowGolemEntity_floatingMixin extends Entity {

    public SnowGolemEntity_floatingMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(
            method = "tickMovement()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;getDefaultState()Lnet/minecraft/block/BlockState;",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void cancelIfNotOnGround(CallbackInfo ci) {
        if (CFSettings.snowmanCreateSnowWhileFloatingFix && !this.onGround) ci.cancel();
    }
}
