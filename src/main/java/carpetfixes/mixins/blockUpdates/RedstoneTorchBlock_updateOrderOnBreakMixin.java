package carpetfixes.mixins.blockUpdates;

import carpetfixes.CarpetFixesSettings;
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
            method = "onStateReplaced(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Z)V",
            at = @At("HEAD")
    )
    public void onStateReplacedUpdateNextFirst(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved, CallbackInfo ci) {
        if (CarpetFixesSettings.redstoneTorchOrderOnBreakFix && !moved) {
            world.updateNeighbors(pos,self); //TODO: Remake the entire redstone torch updates
        }
    }
}
