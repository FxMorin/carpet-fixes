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
    @Shadow private boolean inOpenWater;
    @Shadow private FishingBobberEntity.State state;
    @Shadow private int hookCountdown;

    @Redirect(method= "tick()V",at=@At(value="INVOKE",target="Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/tag/Tag;)Z"))
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
