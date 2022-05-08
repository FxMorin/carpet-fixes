package carpetfixes.mixins.blockUpdates;

import carpetfixes.CFSettings;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.DetectorRailBlock;
import net.minecraft.block.enums.RailShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DetectorRailBlock.class)
public abstract class DetectorRailBlock_invalidUpdateMixin extends AbstractRailBlock {

    protected DetectorRailBlock_invalidUpdateMixin(boolean allowCurves, Settings settings) {
        super(allowCurves, settings);
    }


    @Inject(
            method = "onBlockAdded(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;" +
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Z)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/DetectorRailBlock;updateCurves(Lnet/minecraft/block/BlockState;" +
                            "Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Z)" +
                            "Lnet/minecraft/block/BlockState;",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void updateNeighborsExceptWithBetterDirection(BlockState state, World world, BlockPos pos,
                                                          BlockState oldState, boolean notify, CallbackInfo ci) {
        if (CFSettings.railInvalidUpdateOnPushFix) {
            RailShape railShape = state.get(this.getShapeProperty());
            if (shouldDropRail(pos, world, railShape)) {
                dropStacks(state, world, pos);
                world.removeBlock(pos, notify);
                ci.cancel();
            }
        }
    }

    private static boolean shouldDropRail(BlockPos pos, World world, RailShape shape) {
        if (hasTopRim(world, pos.down())) {
            return switch (shape) {
                case ASCENDING_EAST -> !hasTopRim(world, pos.east());
                case ASCENDING_WEST -> !hasTopRim(world, pos.west());
                case ASCENDING_NORTH -> !hasTopRim(world, pos.north());
                case ASCENDING_SOUTH -> !hasTopRim(world, pos.south());
                default -> false;
            };
        }
        return true;
    }
}
