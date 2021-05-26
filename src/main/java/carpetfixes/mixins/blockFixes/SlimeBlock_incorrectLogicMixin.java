package carpetfixes.mixins.blockFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.SlimeBlock;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlimeBlock.class)
public class SlimeBlock_incorrectLogicMixin {

    @Inject(method = "bounce(Lnet/minecraft/entity/Entity;)V", at = @At(value="INVOKE", target="Lnet/minecraft/entity/Entity;setVelocity(DDD)V"))
    public void entityHittingSlimeBlockBeLike(Entity entity, CallbackInfo ci){
        if (CarpetFixesSettings.incorrectBounceLogicFix) {
            entity.setOnGround(entity.getVelocity().y > -0.15);
        }
    }
}
