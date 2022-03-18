package carpetfixes.mixins.blockUpdates;

import carpetfixes.helpers.BlockUpdateUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/class_7159$class_7161") // CollectingNeighborUpdater$MultiNeighborUpdate
public class class_7159$class_7161_updateOrderMixin {

    /**
     * This fix is based on the block update order. Vanilla block update order is XYZ, resulting in directional
     * behaviour. In this fix we change the block update order to XZY, which fixes all directional problems based
     * on block updates.
     * We also add parity Random Block Update Order here as a meme, through BlockUpdateUtils.blockUpdateDirections
     */


    @Shadow
    @Final
    private BlockPos field_37834;

    @Shadow
    private int field_37837;

    @Shadow
    @Final
    private Block field_37835;

    @Shadow
    @Final
    private @Nullable Direction field_37836;


    @Inject(
            method = "method_41707(Lnet/minecraft/server/world/ServerWorld;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void customRunNext(ServerWorld serverWorld, CallbackInfoReturnable<Boolean> cir) {
        Direction[] directions = BlockUpdateUtils.blockUpdateDirections.apply(this.field_37834);
        BlockPos blockPos = this.field_37834.offset(directions[this.field_37837++]);
        BlockState blockState = serverWorld.getBlockState(blockPos);
        blockState.neighborUpdate(serverWorld, blockPos, this.field_37835, this.field_37834, false);
        if (this.field_37837 < directions.length && directions[this.field_37837] == this.field_37836)
            ++this.field_37837;
        cir.setReturnValue(this.field_37837 < directions.length);
    }
}
