package carpetfixes.mixins.blockUpdates;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.HopperBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * The hopper when placed next to a power source does not give a block update, causing some unintended behaviour
 * where you can update suppress on place. We make sure to give that correct update here by adding binary
 * 1 (NOTIFY_NEIGHBORS) to the update value. This will add a block updates, fixing all the issues.
 *
 * For the invisible hopper, we remove 4 (NO_REDRAW) & add 2 (NOTIFY_LISTENERS) in order for the client to get an
 * update for visuals, without changing block updates
 */

@Mixin(HopperBlock.class)
public class HopperBlock_missingUpdateMixin extends Block {

    public HopperBlock_missingUpdateMixin(Settings settings) {
        super(settings);
    }


    @ModifyArg(
            method = "updateEnabled(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;" +
                    "Lnet/minecraft/block/BlockState;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;" +
                            "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"
            ),
            index = 2
    )
    protected int hopperUpdate(int value) {
        if (CFSettings.hopperUpdateFix) return value | Block.NOTIFY_NEIGHBORS;
        if (CFSettings.invisibleHopperFix) return (value & ~Block.NO_REDRAW) | Block.NOTIFY_LISTENERS;
        return value;
    }
}
