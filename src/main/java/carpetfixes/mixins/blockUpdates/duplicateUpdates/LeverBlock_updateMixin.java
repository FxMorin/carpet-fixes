package carpetfixes.mixins.blockUpdates.duplicateUpdates;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.WallMountedBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fix duplicate block updates in the lever code
 */

@Mixin(LeverBlock.class)
public abstract class LeverBlock_updateMixin extends WallMountedBlock {

    protected LeverBlock_updateMixin(Settings settings) {super(settings);}


    @ModifyArg(
            method = "togglePower",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;" +
                            "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"
            ),
            index = 2
    )
    private int cf$modifyUpdate(int val) {
        return CFSettings.duplicateBlockUpdatesFix ? val & ~Block.NOTIFY_NEIGHBORS : val;
    }


    @Inject(
            method = "updateNeighbors(Lnet/minecraft/block/BlockState;" +
                    "Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cf$updateNeighborsBetter(BlockState state, World world, BlockPos pos, CallbackInfo ci) {
        if (CFSettings.duplicateBlockUpdatesFix) {
            world.updateNeighborsAlways(pos, (LeverBlock)(Object)this);
            Direction dir = getDirection(state);
            if (CFSettings.uselessSelfBlockUpdateFix) {
                world.updateNeighborsExcept(pos.offset(dir.getOpposite()), (LeverBlock)(Object)this, dir);
            } else {
                world.updateNeighborsAlways(pos.offset(dir.getOpposite()), (LeverBlock)(Object)this);
            }
            ci.cancel();
        }
    }
}
