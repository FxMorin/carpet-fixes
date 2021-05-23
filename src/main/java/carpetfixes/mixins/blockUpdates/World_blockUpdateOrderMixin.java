package carpetfixes.mixins.blockUpdates;

import carpetfixes.CarpetFixesInit;
import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public class World_blockUpdateOrderMixin {

    @Shadow public void updateNeighbor(BlockPos sourcePos, Block sourceBlock, BlockPos neighborPos){}

    @Inject(method = "updateNeighborsAlways(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V", at = @At("HEAD"), cancellable = true)
    private void updateNeighborsAlwaysWithBetterDirection(BlockPos pos, Block block, CallbackInfo ci) {
        if (CarpetFixesSettings.blockUpdateOrderFix) {
            for(int dirNum = 0; dirNum < 6; ++dirNum) {
                this.updateNeighbor(pos.offset(CarpetFixesInit.directions[dirNum]), block, pos);
            }
            ci.cancel();
        }
    }

    @Inject(method = "updateNeighborsExcept(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;Lnet/minecraft/util/math/Direction;)V", at = @At("HEAD"), cancellable = true)
    private void updateNeighborsExceptWithBetterDirection(BlockPos pos, Block sourceBlock, Direction direction, CallbackInfo ci) {
        if (CarpetFixesSettings.blockUpdateOrderFix) {
            for(int dirNum = 0; dirNum < 6; ++dirNum) {
                Direction dir = CarpetFixesInit.directions[dirNum];
                if (direction != dir) {
                    this.updateNeighbor(pos.offset(CarpetFixesInit.directions[dirNum]), sourceBlock, pos);
                }
            }
            ci.cancel();
        }
    }
}
