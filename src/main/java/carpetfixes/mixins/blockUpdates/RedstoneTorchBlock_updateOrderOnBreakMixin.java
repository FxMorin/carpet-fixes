package carpetfixes.mixins.blockUpdates;

import carpetfixes.CFSettings;
import carpetfixes.helpers.BlockUpdateUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RedstoneTorchBlock.class)
public class RedstoneTorchBlock_updateOrderOnBreakMixin {

    private final RedstoneTorchBlock self = (RedstoneTorchBlock)(Object)this;


    @Inject(
            method = "onStateReplaced(Lnet/minecraft/block/BlockState;" +
                    "Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Z)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onStateReplacedUpdateNextFirst(BlockState state, World world, BlockPos pos,
                                               BlockState newState, boolean moved, CallbackInfo ci) {
        if (CFSettings.useCustomRedstoneUpdates) {
            if (!moved) {
                boolean doExtraEarlyUpdate = state.get(RedstoneTorchBlock.LIT) & !newState.isOf(self);
                BlockUpdateUtils.doExtendedBlockUpdates(world, pos, self, doExtraEarlyUpdate, true);
            }
            ci.cancel();
        }
    }
}
