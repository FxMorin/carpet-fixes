package carpetfixes.mixins.debugging;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SculkShriekerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;

@Mixin(SculkShriekerBlock.class)
public class SculkShriekerBlock_stackMixin {


    @Inject(
            method = "getStateForNeighborUpdate(Lnet/minecraft/block/BlockState;" +
                    "Lnet/minecraft/util/math/Direction;Lnet/minecraft/block/BlockState;" +
                    "Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;" +
                    "Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockWithEntity;getStateForNeighborUpdate(" +
                            "Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;" +
                            "Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldAccess;" +
                            "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)" +
                            "Lnet/minecraft/block/BlockState;",
                    shift = At.Shift.BEFORE
            )
    )
    private void generateStackTrace(BlockState state, Direction direction, BlockState neighborState, WorldAccess world,
                                    BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> ci) {
        if (CFSettings.debugStackDepth && world.getBlockState(pos.down()).isOf(Blocks.BARRIER)) {
            long depth = StackWalker.getInstance().walk(Stream::count);
            for (PlayerEntity player : world.getPlayers()) {
                player.sendMessage(Text.of("Stack Depth: "+depth),false);
            }
        }
    }
}
