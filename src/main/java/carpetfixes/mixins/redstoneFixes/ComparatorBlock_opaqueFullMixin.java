package carpetfixes.mixins.redstoneFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComparatorBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ComparatorBlock.class)
public class ComparatorBlock_opaqueFullMixin {

    /*
     * // TODO: Add the javadoc at some point xD
     * The Bug:
     * Pistons & Observers are seen as transparent blocks by comparators, and so comparators cannot get comparator
     * outputs from the blocks behind them. Even though the piston, and observer are full cubes. The only reason that
     * observers & pistons are not solid is due to redstone and how it interacts with them. This is a by-product
     *
     * The Fix:
     * Having individual checks for the piston & observer block in the comparator is called bad programming. Most
     * transparent blocks in the game have a setting called `opaque` set to false. Except for `PistonBlock`,
     * `ObserverBlock`, and `RedstoneBlock`. These 3 blocks are transparent but opaque!
     * So what I do to fix the problem is switch out the method `isSolidBlock()` to `isOpaqueFullCube()` in the
     * comparator code, that fixes the issue without causing any side-effects!
     */


    @Redirect(
            method = "getPower",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;isSolidBlock(" +
                            "Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Z"
            )
    )
    private boolean checkOpaqueFullCubeInstead(BlockState state, BlockView world, BlockPos pos) {
        return CFSettings.comparatorTransparencyFix ?
                state.isOpaqueFullCube(world, pos) : state.isSolidBlock(world, pos);
    }
}
