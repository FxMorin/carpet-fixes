package carpetfixes.mixins.parity;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PistonBlock.class)
public class PistonBlock_movableLightMixin {


    @Redirect(
            method = "isMovable(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;" +
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;" +
                    "ZLnet/minecraft/util/math/Direction;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;" +
                            "getHardness(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)F"
            )
    )
    private static float movableLight(BlockState state, BlockView view, BlockPos pos) {
        return CFSettings.parityMovableLightBlocks && state.isOf(Blocks.LIGHT) ? 0 : state.getHardness(view, pos);
    }
}
