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
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FlowableFluid.class)
public abstract class FlowableFluid_chainedTickMixin {


    @Shadow protected abstract boolean canFlow(BlockView world, BlockPos fluidPos, BlockState fluidBlockState, Direction flowDirection, BlockPos flowTo, BlockState flowToBlockState, FluidState fluidState, Fluid fluid);

    @Redirect(
            method= "method_15744(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/fluid/FluidState;Lnet/minecraft/block/BlockState;)V",
            at=@At(
                    value="INVOKE",
                    target="Lnet/minecraft/fluid/FlowableFluid;canFlow(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/fluid/FluidState;Lnet/minecraft/fluid/Fluid;)Z"
            )
    )
    private boolean delayNextScheduleTick(FlowableFluid flowableFluid, BlockView world, BlockPos fluidPos, BlockState fluidBlockState, Direction flowDirection, BlockPos flowTo, BlockState flowToBlockState, FluidState fluidState, Fluid fluid) {
        if (CFSettings.instantFluidFlowingFix && ((WorldAccess)world).getFluidTickScheduler().isTicking(flowTo,fluid)) {
            return false;
        }
        return this.canFlow(world,fluidPos,fluidBlockState,flowDirection,flowTo,flowToBlockState,fluidState,fluid);
    }
}
