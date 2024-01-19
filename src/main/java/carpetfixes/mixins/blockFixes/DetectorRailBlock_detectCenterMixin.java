package carpetfixes.mixins.blockFixes;

import carpetfixes.CFSettings;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.block.BlockState;
import net.minecraft.block.DetectorRailBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

/**
 * Fixes detector rails detecting minecarts on the rail too early
 */
@Mixin(DetectorRailBlock.class)
public class DetectorRailBlock_detectCenterMixin {

    // Client Rendering will make the minecart look a bit weird due to interpolation


    @Inject(
            method = "updatePoweredStatus",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/DetectorRailBlock;getCarts(Lnet/minecraft/world/World;" +
                            "Lnet/minecraft/util/math/BlockPos;Ljava/lang/Class;Ljava/util/function/Predicate;)" +
                            "Ljava/util/List;",
                    shift = At.Shift.BEFORE
            )
    )
    private void cf$getRailPos(World world, BlockPos pos, BlockState state, CallbackInfo ci,
                               @Share("pos") LocalRef<BlockPos> posRef) {
        posRef.set(pos);
    }


    @ModifyArg(
            method = "updatePoweredStatus",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/DetectorRailBlock;getCarts(Lnet/minecraft/world/World;" +
                            "Lnet/minecraft/util/math/BlockPos;Ljava/lang/Class;Ljava/util/function/Predicate;)" +
                            "Ljava/util/List;"
            ),
            index = 3
    )
    private Predicate<Entity> cf$detectIfOnRail(Predicate<Entity> entityPredicate,
                                                @Share("pos") LocalRef<BlockPos> posRef) {
        return CFSettings.detectorRailDetectsTooEarlyFix ? e -> e.getBlockPos().equals(posRef.get()) : entityPredicate;
    }
}
