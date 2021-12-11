package carpetfixes.mixins.parity;

import carpetfixes.CarpetFixesSettings;
import carpetfixes.helpers.Utils;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value=World.class,priority=990)
public class World_randomUpdateOrderMixin {

    @Shadow public void updateNeighbor(BlockPos sourcePos, Block sourceBlock, BlockPos neighborPos){}


    @Inject(
            method = "updateNeighborsAlways(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void updateNeighborsAlwaysWithRandomDirection(BlockPos pos, Block block, CallbackInfo ci) {
        if (CarpetFixesSettings.parityRandomBlockUpdates) {
            for(Direction direction : Utils.randomDirectionArray(pos)) {
                this.updateNeighbor(pos.offset(direction), block, pos);
            }
            ci.cancel();
        }
    }


    @Inject(
            method = "updateNeighborsExcept(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;Lnet/minecraft/util/math/Direction;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void updateNeighborsExceptWithRandomDirection(BlockPos pos, Block sourceBlock, Direction direction, CallbackInfo ci) {
        if (CarpetFixesSettings.parityRandomBlockUpdates) {
            for(Direction dir : Utils.randomDirectionArray(pos)) {
                if (direction != dir) this.updateNeighbor(pos.offset(dir), sourceBlock, pos);
            }
            ci.cancel();
        }
    }
}
