package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.SlimeBlock;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes incorrect logic in the slimeblock bounce code which prevents some entities from bouncing.
 * This bug in the slime code is due to onGround reversing the velocity, the best way to fix this issue is by
 * setting onGround to true, only once the Y velocity is smaller than -0.15
 */

@Mixin(SlimeBlock.class)
public class SlimeBlock_incorrectLogicMixin {


    @Inject(
            method = "bounce(Lnet/minecraft/entity/Entity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;setVelocity(DDD)V"
            )
    )
    private void cf$entityHittingSlimeBlockBeLike(Entity entity, CallbackInfo ci) {
        if (CFSettings.incorrectBounceLogicFix) {
            entity.setOnGround(entity.getVelocity().y > -0.15);
        }
    }
}
