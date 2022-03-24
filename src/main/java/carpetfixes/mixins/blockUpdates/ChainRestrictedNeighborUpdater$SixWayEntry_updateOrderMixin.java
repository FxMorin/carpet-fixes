package carpetfixes.mixins.blockUpdates;

import carpetfixes.helpers.BlockUpdateUtils;
import carpetfixes.settings.VersionPredicates;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Restriction(require = @Condition(value = "minecraft", versionPredicates = VersionPredicates.GT_22w11a))
@Mixin(targets = "net/minecraft/world/block/ChainRestrictedNeighborUpdater$SixWayEntry")
public class ChainRestrictedNeighborUpdater$SixWayEntry_updateOrderMixin {

    /**
     * This fix is based on the block update order. Vanilla block update order is XYZ, resulting in directional
     * behaviour. In this fix we change the block update order to XZY, which fixes all directional problems based
     * on block updates.
     * We also add parity Random Block Update Order here as a meme, through BlockUpdateUtils.blockUpdateDirections
     */


    @Shadow
    @Final
    private BlockPos pos;

    @Shadow
    private int currentDirectionIndex;

    @Shadow
    @Final
    private Block sourceBlock;

    @Shadow
    @Final
    private @Nullable Direction except;


    @Inject(
            method = "update(Lnet/minecraft/world/World;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void customRunNext(World world, CallbackInfoReturnable<Boolean> cir) {
        Direction[] directions = BlockUpdateUtils.blockUpdateDirections.apply(this.pos);
        BlockPos blockPos = this.pos.offset(directions[this.currentDirectionIndex++]);
        BlockState blockState = world.getBlockState(blockPos);
        blockState.neighborUpdate(world, blockPos, this.sourceBlock, this.pos, false);
        if (this.currentDirectionIndex < directions.length && directions[this.currentDirectionIndex] == this.except)
            ++this.currentDirectionIndex;
        cir.setReturnValue(this.currentDirectionIndex < directions.length);
    }
}

@Restriction(require = @Condition(value = "minecraft", versionPredicates = VersionPredicates.LT_22w12a))
@Mixin(targets = "net/minecraft/world/block/ChainRestrictedNeighborUpdater$SixWayEntry")
class ChainRestrictedNeighborUpdater$SixWayEntry_oldUpdateOrderMixin {

    @Shadow
    @Final
    private BlockPos pos;

    @Shadow
    private int currentDirectionIndex;

    @Shadow
    @Final
    private Block sourceBlock;

    @Shadow
    @Final
    private @Nullable Direction except;


    @SuppressWarnings("all")
    @Inject(
            method = "update(Lnet/minecraft/server/world/ServerWorld;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void customRunNext(ServerWorld serverWorld, CallbackInfoReturnable<Boolean> cir) {
        Direction[] directions = BlockUpdateUtils.blockUpdateDirections.apply(this.pos);
        BlockPos blockPos = this.pos.offset(directions[this.currentDirectionIndex++]);
        BlockState blockState = serverWorld.getBlockState(blockPos);
        blockState.neighborUpdate(serverWorld, blockPos, this.sourceBlock, this.pos, false);
        if (this.currentDirectionIndex < directions.length && directions[this.currentDirectionIndex] == this.except)
            ++this.currentDirectionIndex;
        cir.setReturnValue(this.currentDirectionIndex < directions.length);
    }
}
