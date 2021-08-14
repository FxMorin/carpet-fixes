package carpetfixes.mixins.entityFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntity_outsideWaterMixin {

    /**
     * Fishing Bobber is able to fish when outside of water if it catches a fish in a floating
     * water, then falls though and lands on the ground. Here we are able to fix that bug by
     * setting the `inOpenWater` to false & `hookCountdown` to 0 when the state changes for
     * fluid checks.
     */


    @Shadow private boolean inOpenWater;
    @Shadow private FishingBobberEntity.State state;
    @Shadow private int hookCountdown;


    @Redirect(
            method= "tick()V",
            at=@At(
                    value="INVOKE",
                    target="Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/tag/Tag;)Z"
            ))
    private boolean betterTick(FluidState fluidState, Tag<Fluid> tag) {
        boolean state = fluidState.isIn(FluidTags.WATER);
        if (CarpetFixesSettings.fishingOutsideWaterFix && !state) {
            this.state = FishingBobberEntity.State.FLYING;
            this.inOpenWater = false;
            this.hookCountdown = 0;
        }
        return state;
    }
}
