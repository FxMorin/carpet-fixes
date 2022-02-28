package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.enums.ChestType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBlock.class)
public class ChestBlock_worldBorderMixin {

    @Shadow
    @Final
    public static DirectionProperty FACING;

    @Shadow
    @Final
    public static EnumProperty<ChestType> CHEST_TYPE;

    ChestBlock self = (ChestBlock)(Object)this;


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
                BlockState blockState = ctx.getWorld().getBlockState(blockPos);
                if (blockState.isOf(self) && blockState.get(CHEST_TYPE) == ChestType.SINGLE)
                    cir.setReturnValue(blockState.get(FACING));
            }
            cir.setReturnValue(null);
        }
    }
}
