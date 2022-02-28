package carpetfixes.mixins.blockUpdates;

import carpetfixes.helpers.BlockUpdateUtils;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public class World_blockUpdateOrderMixin {

    /**
     * This fix is based on the block update order. Vanilla block update order is XYZ, resulting in directional
     * behaviour. In this fix we change the block update order to XZY, which fixes all directional problems based
     * on block updates.
     */


    private final World self = (World)(Object)this;


    @Inject(
            method = "updateNeighborsAlways(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void updateNeighborsAlwaysWithBetterDirection(BlockPos pos, Block block, CallbackInfo ci) {
        if (BlockUpdateUtils.canUpdateNeighborsAlwaysWithOrder(self, pos, block)) ci.cancel();
    }


    @Inject(
            method = "updateNeighborsExcept(Lnet/minecraft/util/math/BlockPos;" +
                    "Lnet/minecraft/block/Block;Lnet/minecraft/util/math/Direction;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void updateNeighborsExceptWithBetterDirection(BlockPos pos, Block block, Direction dir, CallbackInfo ci) {
        if (BlockUpdateUtils.canUpdateNeighborsExceptWithOrder(self, pos, block, dir)) ci.cancel();
    }
}
