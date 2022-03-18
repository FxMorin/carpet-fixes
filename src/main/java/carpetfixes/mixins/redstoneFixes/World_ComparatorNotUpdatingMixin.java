package carpetfixes.mixins.redstoneFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public class World_ComparatorNotUpdatingMixin {

    /**
     * Move comparator updates over to neighbor updates like everything else.
     * To do this we first prevent the other updates from working, then add the comparator update if they have a
     * comparator output into updateNeighbor()
     */


    @Shadow
    public void updateComparators(BlockPos pos, Block block) {}

    @Shadow
    public BlockState getBlockState(BlockPos pos) {
        return null;
    }


    @Redirect(
            method = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;II)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;hasComparatorOutput()Z"
            )
    )
    private boolean updateNeighborsAlwaysWithBetterDirection(BlockState blockState) {
        return !CFSettings.comparatorUpdateFix && blockState.hasComparatorOutput();
    }


    @Inject(
            method = "updateNeighbor(Lnet/minecraft/util/math/BlockPos;" +
                    "Lnet/minecraft/block/Block;Lnet/minecraft/util/math/BlockPos;)V",
            at = @At("HEAD")
    )
    private void updateNeighborsAlwaysWithBetterDirection(BlockPos sourcePos, Block sourceBlock,
                                                          BlockPos neighborPos, CallbackInfo ci) {
        if (CFSettings.comparatorUpdateFix) {
            if(sourceBlock.getDefaultState().hasComparatorOutput() ||
                    this.getBlockState(sourcePos).hasComparatorOutput())
                this.updateComparators(sourcePos, sourceBlock);
        }
    }
}
