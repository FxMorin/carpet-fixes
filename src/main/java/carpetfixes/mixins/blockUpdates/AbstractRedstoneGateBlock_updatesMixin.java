package carpetfixes.mixins.blockUpdates;

import carpetfixes.CFSettings;
import carpetfixes.helpers.BlockUpdateUtils;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractRedstoneGateBlock.class)
public class AbstractRedstoneGateBlock_updatesMixin {


    @Inject(
            method = "neighborUpdate(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;" +
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;" +
                    "Lnet/minecraft/util/math/BlockPos;Z)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    public void neighborUpdate(BlockState state, World world, BlockPos pos,
                               Block block,BlockPos fromPos, boolean notify, CallbackInfo ci) {
        if (CFSettings.useCustomRedstoneUpdates) {
            BlockUpdateUtils.doExtendedBlockUpdates(world, pos, block, state.get(AbstractRedstoneGateBlock.POWERED), false);
            ci.cancel();
        }
    }
}
