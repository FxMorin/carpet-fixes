package carpetfixes.mixins.blockEntityFixes;

import carpetfixes.CFSettings;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Fixes pistons pushing entities 0.01 too far instead of an exact block
 */
@Mixin(PistonBlockEntity.class)
public class PistonBlockEntity_pushTooFarMixin {


    @Inject(
            method = "pushEntities",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/shape/VoxelShape;getBoundingBoxes()Ljava/util/List;"
            )
    )
    private static void cf$isFinalTick(World world, BlockPos pos, float f, PistonBlockEntity be, CallbackInfo ci,
                                    @Share("finalTick") LocalBooleanRef finalTickRef) {
        finalTickRef.set(CFSettings.pistonsPushTooFarFix && f >= 1.0);
    }


    @ModifyConstant(
            method = "pushEntities",
            constant = @Constant(doubleValue = 0.01)
    )
    private static double cf$dontPushOffsetLastTick(double constant,
                                                    @Share("finalTick") LocalBooleanRef finalTickRef) {
        return finalTickRef.get() ? 0.0 : constant;
    }
}
