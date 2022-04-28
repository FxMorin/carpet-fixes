package carpetfixes.mixins.blockFixes.doubleBlocksSkipEntityChecks;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DoorBlock.class)
public class DoorBlock_entityCheckMixin {


    @Inject(
            method = "getPlacementState",
            at = @At(
                    value = "RETURN",
                    ordinal = 0
            ),
            cancellable = true
    )
    private void canPlaceBed(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        if (CFSettings.doubleBlocksSkipEntityCheckFix &&
                !ctx.getWorld().canPlace(cir.getReturnValue(), ctx.getBlockPos().up(), ShapeContext.absent()))
            cir.setReturnValue(null);
    }
}
