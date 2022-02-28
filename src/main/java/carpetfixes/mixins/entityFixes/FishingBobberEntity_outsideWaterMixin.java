package carpetfixes.mixins.entityFixes;

import carpetfixes.CFSettings;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntity_outsideWaterMixin {

    /**
     * Fishing Bobber is able to fish when outside of water if it catches a fish in a floating water, then falls though
     * and lands on the ground. Here we are able to fix that bug by setting the `inOpenWater` to false &
     * `hookCountdown` to 0 when the state changes for fluid checks.
     */


    @Shadow
    private boolean inOpenWater;

    @Shadow
    private FishingBobberEntity.State state;

    @Shadow
    private int hookCountdown;


    @Redirect(
            method = "tick()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getFluidState(Lnet/minecraft/util/math/BlockPos;)" +
                            "Lnet/minecraft/fluid/FluidState;"
            )
    )
    private FluidState checkAfterBeingSet(World instance, BlockPos pos) {
        FluidState state = instance.getFluidState(pos);
        if (CFSettings.fishingOutsideWaterFix && !state.isIn(FluidTags.WATER)) {
            this.state = FishingBobberEntity.State.FLYING;
            this.inOpenWater = false;
            this.hookCountdown = 0;
        }
        return state;
    }
}
