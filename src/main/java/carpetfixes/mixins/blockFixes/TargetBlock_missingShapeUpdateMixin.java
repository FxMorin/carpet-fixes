package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TargetBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TargetBlock.class)
public class TargetBlock_missingShapeUpdateMixin {


    @Inject(
            method = "onBlockAdded(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;" +
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Z)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;" +
                            "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z",
                    shift = At.Shift.AFTER
            )
    )
    private void targetBlockUpdate(BlockState state, World world, BlockPos pos,
                                   BlockState oldState, boolean notify, CallbackInfo ci) {
        if (CFSettings.targetBlockUpdateFix) state.updateNeighbors(world, pos, Block.NOTIFY_LISTENERS);
    }
}
