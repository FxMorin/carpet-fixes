package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class Entity_incorrectLogicMixin {

    @Shadow public abstract void setOnGround(boolean onGround);

    @Inject(method = "onBubbleColumnCollision(Z)V", at = @At("HEAD"), cancellable = true)
    public void onBubbleColumnCollision(boolean drag, CallbackInfo ci) {
        if (CarpetFixesSettings.incorrectBubbleColumnLogicFix) {
            this.setOnGround(false);
        }
    }

    @Inject(method = "onBubbleColumnSurfaceCollision(Z)V", at = @At("HEAD"), cancellable = true)
    public void onBubbleColumnSurfaceCollision(boolean drag, CallbackInfo ci) {
        if (CarpetFixesSettings.incorrectBubbleColumnLogicFix) {
            this.setOnGround(false);
        }
    }
}
