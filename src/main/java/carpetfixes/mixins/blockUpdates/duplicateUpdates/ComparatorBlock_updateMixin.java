package carpetfixes.mixins.blockUpdates.duplicateUpdates;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComparatorBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ComparatorBlock.class)
public abstract class ComparatorBlock_updateMixin {


    @Inject(
            method = "update",
            at = @At(
                    shift = At.Shift.BEFORE,
                    value = "INVOKE",
                    target="Lnet/minecraft/block/ComparatorBlock;updateTarget(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V"
            ),
            cancellable = true
    )
    private void removeUpdate(World world, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (CarpetFixesSettings.duplicateBlockUpdatesFix) ci.cancel();
    }
}
