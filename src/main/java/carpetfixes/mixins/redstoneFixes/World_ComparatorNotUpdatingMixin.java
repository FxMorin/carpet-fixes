package carpetfixes.mixins.redstoneFixes;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public class World_ComparatorNotUpdatingMixin {

    @Shadow public void updateComparators(BlockPos pos, Block block) {}

    @Shadow public BlockState getBlockState(BlockPos pos) { return null;}

    @Inject(method = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;II)Z", at = @At(value ="INVOKE",target = "Lnet/minecraft/block/BlockState;hasComparatorOutput()Z"), cancellable = true)
    private void updateNeighborsAlwaysWithBetterDirection(BlockPos pos, BlockState state, int flags, int maxUpdateDepth, CallbackInfoReturnable<Boolean> cir) {
        if (CarpetFixesSettings.comparatorUpdateFix) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "updateNeighbor(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;Lnet/minecraft/util/math/BlockPos;)V", at = @At(value ="INVOKE",target = "Lnet/minecraft/block/BlockState;neighborUpdate(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;Lnet/minecraft/util/math/BlockPos;Z)V"))
    private void updateNeighborsAlwaysWithBetterDirection(BlockPos sourcePos, Block sourceBlock, BlockPos neighborPos, CallbackInfo ci) {
        if (CarpetFixesSettings.comparatorUpdateFix) {
            BlockState newState = this.getBlockState(sourcePos);
            if(sourceBlock.getDefaultState().hasComparatorOutput() || newState.hasComparatorOutput()) {
                this.updateComparators(sourcePos, sourceBlock);
            }
        }
    }
}
