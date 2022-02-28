package carpetfixes.mixins.reIntroduced;

import carpetfixes.CFSettings;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFireBlock.class)
public class AbstractFireBlock_flintAndSteelMixin {

    /**
     * Allows flint and steel to be used against the side of blocks. Useful for flying machines
     * and nether portals.
     */


    @Inject(
            method = "canPlaceAt",
            require = 0,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;canPlaceAt(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z"
            ),
            cancellable = true
    )
    private static void canPlaceAt(World world, BlockPos blockPos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if(CFSettings.reIntroduceFlintAndSteelBehavior && world.getBlockState(blockPos.down()).getBlock() != Blocks.FIRE) cir.setReturnValue(true);
    }
}
