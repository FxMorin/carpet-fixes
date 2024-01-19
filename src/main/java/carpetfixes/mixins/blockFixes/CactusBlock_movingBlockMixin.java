package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

/**
 * Fixes cactus breaking when touching a moving piston, it does not do the correct check for pistons. So we make sure
 * to get the correct blockState from within the piston block entity and running the check against that instead.
 */
@Mixin(CactusBlock.class)
public class CactusBlock_movingBlockMixin {


    @Redirect(
            method = "canPlaceAt(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldView;" +
                    "Lnet/minecraft/util/math/BlockPos;)Z",
            slice = @Slice(
                    from = @At("HEAD"),
                    to = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/util/math/BlockPos;down()Lnet/minecraft/util/math/BlockPos;"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/WorldView;getBlockState(Lnet/minecraft/util/math/BlockPos;)" +
                            "Lnet/minecraft/block/BlockState;"
            )
    )
    private BlockState cf$getCorrectBlockStateIfMovingPiston(WorldView world, BlockPos pos) {
        if (CFSettings.nonSolidBlocksBreakCactusIfPushedFix) {
            BlockState state = world.getBlockState(pos);
            if (state.isOf(Blocks.MOVING_PISTON) && world.getBlockEntity(pos) instanceof PistonBlockEntity pbe) {
                return pbe.getPushedBlock();
            }
            return state;
        }
        return world.getBlockState(pos);
    }
}
