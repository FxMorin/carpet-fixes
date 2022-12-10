package carpetfixes.mixins.blockFixes.doubleBlocksSkipEntityChecks;

import carpetfixes.CFSettings;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Fixes being able to place a double block within an entity due to a missing check in the second block
 */
@Mixin(BedBlock.class)
public class BedBlock_entityChecksMixin {


    @Inject(
            method = "getPlacementState",
            locals = LocalCapture.CAPTURE_FAILSOFT,
            at = @At("RETURN"),
            cancellable = true
    )
    private void canPlaceBed(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir,
                             Direction direction, BlockPos pos, BlockPos pos2, World world) {
        if (CFSettings.doubleBlocksSkipEntityCheckFix && cir.getReturnValue() != null &&
                !world.canPlace(cir.getReturnValue(), pos2, ShapeContext.absent()))
            cir.setReturnValue(null);
    }
}
