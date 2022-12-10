package carpetfixes.mixins.redstoneFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.block.enums.RailShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static net.minecraft.block.DetectorRailBlock.POWERED;

/**
 * Fixes detector rails not being able to power diagonal powered rails
 */

@Mixin(PoweredRailBlock.class)
public class PoweredRailBlock_detectorRailMixin {


    @Inject(
            method = "isPoweredByOtherRails(Lnet/minecraft/world/World;" +
                    "Lnet/minecraft/util/math/BlockPos;ZILnet/minecraft/block/enums/RailShape;)Z",
            locals = LocalCapture.CAPTURE_FAILSOFT,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z",
                    shift = At.Shift.BEFORE,
                    ordinal = 0
            ),
            cancellable = true
    )
    private void isPoweredByOtherRails(World world, BlockPos pos, boolean arg2, int arg3, RailShape arg4,
                                       CallbackInfoReturnable<Boolean> cir, BlockState blockState) {
        if (CFSettings.detectorRailsDontPowerDiagonallyFix && blockState.isOf(Blocks.DETECTOR_RAIL))
            cir.setReturnValue(blockState.get(POWERED));
    }
}
