package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes lava treating bubble columns as full blocks
 */

@Mixin(LavaFluid.class)
public class LavaFluid_bubbleColumnMixin {


    @Inject(
            method = "flow",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/LavaFluid;playExtinguishEvent" +
                            "(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;)V",
                    shift = At.Shift.BEFORE
            )
    )
    private void moreFlow(WorldAccess world, BlockPos blockPos, BlockState blockState, Direction direction,
                        FluidState fluidState, CallbackInfo ci) {
        if (CFSettings.lavaIgnoresBubbleColumnFix && blockState.getBlock() instanceof BubbleColumnBlock)
            world.setBlockState(blockPos, Blocks.STONE.getDefaultState(), Block.NOTIFY_ALL);
    }
}
