package carpetfixes.mixins.blockUpdates;

import net.minecraft.block.RedstoneWireBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RedstoneWireBlock.class)
public class RedstoneWireBlock_extendedUpdatesMixin {

    //@Shadow private void updateOffsetNeighbors(World world, BlockPos pos){}
    //@Shadow private void update(World world, BlockPos pos, BlockState state){}

    //private final RedstoneWireBlock self = (RedstoneWireBlock)(Object)this;


    //TODO: Requires More Testing before releasing!
    /*@Inject(
            method = "updateNeighbors(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;updateNeighborsAlways(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private void updateNeighbors(World world, BlockPos pos, CallbackInfo ci) {
        BlockUpdateUtils.doExtendedBlockUpdates(world,pos,self,false,false);
        ci.cancel();
    }*/

    //TODO: Requires More Testing before releasing!
    /*@Inject(
            method = "onStateReplaced(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Z)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;onStateReplaced(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Z)V",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private void updateNeighbors(World world, BlockPos pos, CallbackInfo ci) {
        if (!world.isClient) {
		    BlockUpdateUtils.doExtendedBlockUpdates(world,pos,self,false,false);
			this.update(world, pos, state);
			this.updateOffsetNeighbors(world, pos);
		}
    }*/
}
