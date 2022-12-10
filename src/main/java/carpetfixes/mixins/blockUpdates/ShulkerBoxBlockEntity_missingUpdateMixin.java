package carpetfixes.mixins.blockUpdates;

import carpetfixes.CFSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Adds a shape update when the shulker box opens
 */

@Mixin(ShulkerBoxBlockEntity.class)
public class ShulkerBoxBlockEntity_missingUpdateMixin {


    @Inject(
            method = "updateNeighborStates",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void updateNeighborStates(World world, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (CFSettings.shulkerBoxMissingUpdateFix) {
            world.updateNeighborsAlways(pos, state.getBlock());
            ci.cancel();
        }
    }
}
