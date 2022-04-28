package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.*;
import net.minecraft.block.enums.Thickness;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static net.minecraft.block.PointedDripstoneBlock.THICKNESS;
import static net.minecraft.block.PointedDripstoneBlock.VERTICAL_DIRECTION;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlock_entityChecksMixin extends Block {

    public PointedDripstoneBlock_entityChecksMixin(Settings settings) {
        super(settings);
    }


    @SuppressWarnings("InvalidInjectorMethodSignature")
    @Inject(
            method = "getPlacementState",
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;" +
                            "Ljava/lang/Comparable;)Ljava/lang/Object;",
                    ordinal = 2
            ),
            cancellable = true
    )
    private void canPlaceDripstone(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir,
                                   WorldAccess world, BlockPos pos, Direction dir,
                                   Direction dir2, boolean bl, Thickness thickness) {
        if (CFSettings.dripstoneSkipsEntityCheckFix && thickness.ordinal() < 4) {
            BlockPos moveDir = pos.offset(dir2.getOpposite());
            if (world.getBlockState(moveDir).isOf(Blocks.POINTED_DRIPSTONE)) {
                BlockState replaceState = this.getDefaultState().with(VERTICAL_DIRECTION, dir2)
                        .with(THICKNESS, Thickness.values()[thickness.ordinal() + 1]);
                if (!world.canPlace(replaceState, moveDir, ShapeContext.absent())) {
                    cir.setReturnValue(null);
                    return;
                }
                moveDir = moveDir.offset(dir2.getOpposite());
                if (thickness.ordinal() < 3 && world.getBlockState(moveDir).isOf(Blocks.POINTED_DRIPSTONE)) {
                    replaceState = this.getDefaultState().with(VERTICAL_DIRECTION, dir2)
                            .with(THICKNESS, Thickness.values()[thickness.ordinal() + 2]);
                    if (!world.canPlace(replaceState, moveDir, ShapeContext.absent())) cir.setReturnValue(null);
                }
            }
        }
    }
}
