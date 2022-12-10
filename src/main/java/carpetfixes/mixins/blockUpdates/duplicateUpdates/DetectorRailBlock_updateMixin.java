package carpetfixes.mixins.blockUpdates.duplicateUpdates;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.DetectorRailBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Fix duplicate block updates in the detector rail code
 */

@Mixin(DetectorRailBlock.class)
public class DetectorRailBlock_updateMixin {


    @ModifyArg(
            method = "updatePoweredStatus",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;" +
                            "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"
            ),
            index = 2
    )
    private int modifyUpdate(int val) {
        return CFSettings.duplicateBlockUpdatesFix ? val & ~Block.NOTIFY_NEIGHBORS : val;
    }


    @Redirect(
            method = "updatePoweredStatus",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;" +
                            "updateNeighborsAlways(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;)V"
            )
    )
    private void removeUpdate(World world, BlockPos pos, Block block) {
        if (!CFSettings.duplicateBlockUpdatesFix) world.updateNeighborsAlways(pos,block);
    }
}
