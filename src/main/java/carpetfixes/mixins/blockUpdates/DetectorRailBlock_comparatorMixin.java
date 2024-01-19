package carpetfixes.mixins.blockUpdates;

import carpetfixes.CFSettings;
import carpetfixes.helpers.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.DetectorRailBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * There are two fixes here. One to fix some useless block updates that the detector rail gives to itself,
 * the other is to fix the detector rail giving updates to comparators facing the wrong way
 */

@Mixin(DetectorRailBlock.class)
public class DetectorRailBlock_comparatorMixin {

    @Shadow
    @Final
    public static BooleanProperty POWERED;


    @Inject(
            method = "updatePoweredStatus(Lnet/minecraft/world/World;" +
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;" +
                            "updateComparators(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V",
                    shift = At.Shift.BEFORE,
                    ordinal = 0
            ),
            cancellable = true
    )
    private void cf$updateComparatorsSpecial(World world, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (CFSettings.uselessDetectorRailUpdateFix) {
            if (state.get(POWERED)) {
                if (CFSettings.detectorRailOffsetUpdateFix) {
                    Utils.updateComparatorsRespectFacing(world, pos, (DetectorRailBlock)(Object)this);
                } else {
                    world.updateComparators(pos, (DetectorRailBlock)(Object)this);
                }
            }
            ci.cancel();
        } else if (CFSettings.detectorRailOffsetUpdateFix) {
            Utils.updateComparatorsRespectFacing(world, pos, (DetectorRailBlock)(Object)this);
            ci.cancel();
        }
    }
}
