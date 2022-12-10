package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fixes instant liquid flow
 */
@Mixin(FlowableFluid.class)
public abstract class FlowableFluid_chainedTickMixin {


    @Inject(
            method = "canFlow(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;" +
                    "Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;" +
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;" +
                    "Lnet/minecraft/fluid/FluidState;Lnet/minecraft/fluid/Fluid;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void delayNextScheduleTick(BlockView world, BlockPos fluidPos, BlockState fluidBlockState,
                                       Direction flowDirection, BlockPos flowTo, BlockState flowToBlockState,
                                       FluidState fluidState, Fluid fluid, CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.instantFluidFlowingFix && ((WorldAccess)world).getFluidTickScheduler().isTicking(flowTo, fluid))
            cir.setReturnValue(false);
    }
}
