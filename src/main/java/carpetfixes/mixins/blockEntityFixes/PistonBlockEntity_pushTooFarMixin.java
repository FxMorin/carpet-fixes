package carpetfixes.mixins.blockEntityFixes;

import carpetfixes.CFSettings;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PistonBlockEntity.class)
public class PistonBlockEntity_pushTooFarMixin {

    private static boolean finalTick = false;


    @Inject(
            method = "pushEntities",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/shape/VoxelShape;getBoundingBoxes()Ljava/util/List;"
            )
    )
    private static void isFinalTick(World world, BlockPos pos, float f, PistonBlockEntity be, CallbackInfo ci) {
        finalTick = CFSettings.pistonsPushTooFarFix && f >= 1.0;
    }


    @ModifyConstant(
            method = "pushEntities",
            constant = @Constant(doubleValue = 0.01)
    )
    private static double dontPushOffsetLastTick(double constant) {
        return finalTick ? 0.0 : constant;
    }
}
