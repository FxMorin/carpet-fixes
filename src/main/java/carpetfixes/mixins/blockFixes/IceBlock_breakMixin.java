package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IceBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Fixes ice not turning to water when broken above a waterlogged block like when its broken above water
 */

@Mixin(IceBlock.class)
public class IceBlock_breakMixin {


    @Redirect(
            method = "afterBreak(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;" +
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;" +
                    "Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/item/ItemStack;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)" +
                            "Lnet/minecraft/block/BlockState;"
            )
    )
    private BlockState getBlockStateModify(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return CFSettings.iceWaterSkipsWaterloggedFix && state.getFluidState().isStill() ?
                Blocks.WATER.getDefaultState() : state;
    }
}
