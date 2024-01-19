package carpetfixes.mixins.blockUpdates;

import carpetfixes.CFSettings;
import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Removes useless block updates from the pressure plate to itself
 */

@Mixin(AbstractPressurePlateBlock.class)
public class AbstractPressurePlateBlock_uselessMixin {


    @Inject(
            method = "updateNeighbors(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cf$updateNeighbors(World world, BlockPos pos, CallbackInfo ci) {
        if (CFSettings.uselessSelfBlockUpdateFix) {
            world.updateNeighborsAlways(pos, (AbstractPressurePlateBlock)(Object)this);
            world.updateNeighborsExcept(pos.down(), (AbstractPressurePlateBlock)(Object)this, Direction.UP);
            ci.cancel();
        }
    }
}
