package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DirtPathBlock;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DirtPathBlock.class)
public class DirtPathBlock_movingBlockMixin {

    /**
     * Makes it so that path blocks get the blockState that the moving piston is carrying instead of checking the
     * pistons material directly. Should fix a few more bugs!
     */


    @Redirect(
            method = "canPlaceAt(Lnet/minecraft/block/BlockState;" +
                    "Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z",
            at=@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/WorldView;getBlockState(Lnet/minecraft/util/math/BlockPos;)" +
                            "Lnet/minecraft/block/BlockState;"
            )
    )
    private BlockState canPlaceAt(WorldView world, BlockPos pos) {
        if (CFSettings.movingBlocksDestroyPathFix) {
            BlockState state = world.getBlockState(pos);
            if (state.isOf(Blocks.MOVING_PISTON) && world.getBlockEntity(pos) instanceof PistonBlockEntity pbe)
                return pbe.getPushedBlock();
            return state;
        }
        return world.getBlockState(pos);
    }
}
