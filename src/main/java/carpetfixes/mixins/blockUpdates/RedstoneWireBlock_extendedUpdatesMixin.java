package carpetfixes.mixins.blockUpdates;

import carpetfixes.CFSettings;
import carpetfixes.helpers.BlockUpdateUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Use carpet-fixes custom redstone update system instead
 */

@Mixin(RedstoneWireBlock.class)
public class RedstoneWireBlock_extendedUpdatesMixin {

    @Shadow
    private void updateOffsetNeighbors(World world, BlockPos pos) {}

    @Shadow
    private void update(World world, BlockPos pos, BlockState state) {}

    private final RedstoneWireBlock self = (RedstoneWireBlock)(Object)this;


    @Inject(
            method = "updateNeighbors(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;updateNeighborsAlways(Lnet/minecraft/util/math/BlockPos;" +
                            "Lnet/minecraft/block/Block;)V",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private void onUpdateExtendedNeighbors(World world, BlockPos pos, CallbackInfo ci) {
        if (CFSettings.useCustomRedstoneUpdates) {
            BlockUpdateUtils.doExtendedBlockUpdates(world, pos, self, false, false);
            ci.cancel();
        }
    }


    @Inject(
            method = "onStateReplaced(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;" +
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Z)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;onStateReplaced(Lnet/minecraft/block/BlockState;" +
                            "Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;" +
                            "Lnet/minecraft/block/BlockState;Z)V",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private void onStateReplacedExtendedUpdates(BlockState state, World world, BlockPos pos,
                                                BlockState newState, boolean moved, CallbackInfo ci) {
        if (CFSettings.useCustomRedstoneUpdates) {
            if (!world.isClient) {
                boolean doExtraEarlyUpdate = state.get(RedstoneWireBlock.POWER) > 0 && !newState.isOf(self);
                BlockUpdateUtils.doExtendedBlockUpdates(world, pos, self, doExtraEarlyUpdate, false);
                this.update(world, pos, state);
                this.updateOffsetNeighbors(world, pos);
            }
            ci.cancel();
        }
    }
}
