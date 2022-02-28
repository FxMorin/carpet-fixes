package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class Entity_incorrectLogicMixin {

    /**
     * Bubble columns do some incorrect logic calculations based on the Y velocity being
     * multiplied by -1 for being onGround. The fix is to not set the entity to be onGround
     * when in a bubble column.
     */


    @Shadow public abstract void setOnGround(boolean onGround);


    @Inject(
            method = "onBubbleColumnCollision(Z)V",
            at = @At("HEAD")
    )
    public void onBubbleColumnCollision(boolean drag, CallbackInfo ci) {
        if (CFSettings.incorrectBubbleColumnLogicFix) this.setOnGround(false);
    }


    @Inject(
            method = "onBubbleColumnSurfaceCollision(Z)V",
            at = @At("HEAD")
    )
    public void onBubbleColumnSurfaceCollision(boolean drag, CallbackInfo ci) {
        if (CFSettings.incorrectBubbleColumnLogicFix) this.setOnGround(false);
    }
}
