package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirtPathBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.PistonExtensionBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DirtPathBlock.class)
public class DirtPathBlock_movingBlockMixin {

    /**
     * Makes it so that path blocks do not get destroyed if a moving piston is above them.
     * Might want to mixin into the instanceof instead next time
     */


    @Inject(
            method= "canPlaceAt(Lnet/minecraft/block/BlockState;" +
                    "Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z",
            at=@At("HEAD"),
            cancellable = true
    )
    public void canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (CFSettings.movingBlocksDestroyPathFix) {
            BlockState blockState = world.getBlockState(pos.up());
            cir.setReturnValue(
                    !blockState.getMaterial().isSolid() ||
                    blockState.getBlock() instanceof FenceGateBlock ||
                    blockState.getBlock() instanceof PistonExtensionBlock
            );
        }
    }
}
