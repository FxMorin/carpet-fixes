package carpetfixes.mixins.blockUpdates.observerUpdates;

import carpetfixes.CFSettings;
import carpetfixes.helpers.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.CommandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.CommandBlockExecutor;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Add missing observer updates when a command block finishes an execution
 */

@Mixin(CommandBlock.class)
public class CommandBlock_updateMixin {


    @Inject(
            method = "execute(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;" +
                    "Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/CommandBlockExecutor;Z)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/CommandBlockExecutor;execute(Lnet/minecraft/world/World;)Z",
                    shift = At.Shift.AFTER
            )
    )
    private void execute(BlockState state, World world, BlockPos pos,
                         CommandBlockExecutor executor, boolean hasCommand, CallbackInfo ci) {
        if (CFSettings.missingObserverUpdatesFix && executor.getSuccessCount() > 0)
            Utils.giveObserverUpdates(world,pos);
    }
}
