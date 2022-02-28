package carpetfixes.mixins.blockUpdates;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TrapdoorBlock.class)
public abstract class TrapdoorBlock_missingUpdateMixin extends HorizontalFacingBlock {

    @Shadow
    @Final
    public static BooleanProperty OPEN;

    @Shadow
    @Final
    public static EnumProperty<BlockHalf> HALF;

    protected TrapdoorBlock_missingUpdateMixin(Settings settings) {
        super(settings);
    }

    private Direction directionToUpdate(BlockState state) {
        return state.get(OPEN) ?
                ((state.get(HALF) == BlockHalf.TOP) ? Direction.UP : Direction.DOWN) :
                state.get(FACING).getOpposite();
    }


    @Inject(
            method = "onUse",
            at = @At(
                    shift = At.Shift.AFTER,
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;" +
                            "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"
            ),
            cancellable = true
    )
    private void updateOnUseCorrectly(BlockState state, World world, BlockPos pos, PlayerEntity player,
                                      Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (CFSettings.trapdoorMissingUpdateFix)
            world.updateNeighbor(pos.offset(directionToUpdate(state)),state.getBlock(),pos);
    }


    @Inject(
            method = "neighborUpdate",
            at = @At(
                    shift = At.Shift.AFTER,
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;" +
                            "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"
            )
    )
    private void updateCorrectly(BlockState state, World world, BlockPos pos, Block block,
                                 BlockPos fromPos, boolean notify, CallbackInfo ci) {
        if (CFSettings.trapdoorMissingUpdateFix)
            world.updateNeighbor(pos.offset(directionToUpdate(state)),state.getBlock(),pos);
    }
}
