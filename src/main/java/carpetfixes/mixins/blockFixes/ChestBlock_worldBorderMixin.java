package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.ChestBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fixes being able to access a chest past the world border by preventing chest from connecting through the border
 */
@Mixin(ChestBlock.class)
public class ChestBlock_worldBorderMixin {


    @Inject(
            method= "getNeighborChestDirection(Lnet/minecraft/item/ItemPlacementContext;" +
                    "Lnet/minecraft/util/math/Direction;)Lnet/minecraft/util/math/Direction;",
            at=@At("HEAD"),
            cancellable = true
    )
    private void getNeighborChestDirection(ItemPlacementContext ctx, Direction dir,
                                           CallbackInfoReturnable<Direction> cir) {
        if (CFSettings.chestUsablePastWorldBorderFix) {
            BlockPos blockPos = ctx.getBlockPos().offset(dir);
            if (!ctx.getWorld().getWorldBorder().contains(blockPos)) {
                cir.setReturnValue(null);
            }
        }
    }
}
