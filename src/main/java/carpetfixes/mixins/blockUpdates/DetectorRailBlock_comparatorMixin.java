package carpetfixes.mixins.blockUpdates;

import carpetfixes.CarpetFixesSettings;
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

@Mixin(DetectorRailBlock.class)
public class DetectorRailBlock_comparatorMixin {

    @Shadow @Final public static BooleanProperty POWERED;

    DetectorRailBlock self = (DetectorRailBlock)(Object)this;


    @Inject(
            method= "updatePoweredStatus(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V",
            at = @At(
                    value="INVOKE",
                    target="Lnet/minecraft/world/World;updateComparators(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V",
                    shift= At.Shift.BEFORE,
                    ordinal = 0
            ),
            cancellable = true
    )
    public void updateComparatorsSpecial(World world, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (CarpetFixesSettings.uselessDetectorRailUpdateFix) {
            if (state.get(POWERED)) {
                if (CarpetFixesSettings.detectorRailOffsetUpdateFix) {
                    Utils.updateComparatorsRespectFacing(world,pos,self);
                } else {
                    world.updateComparators(pos, self);
                }
            }
            ci.cancel();
        } else if (CarpetFixesSettings.detectorRailOffsetUpdateFix) {
            Utils.updateComparatorsRespectFacing(world,pos,self);
            ci.cancel();
        }
    }
}
