package carpetfixes.mixins.blockUpdates;

import carpetfixes.helpers.BlockUpdateUtils;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.block.NeighborUpdater;
import net.minecraft.world.block.SimpleNeighborUpdater;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Changes the block update order
 */

@Mixin(SimpleNeighborUpdater.class)
public abstract class SimpleNeighborUpdater_updateOrderMixin implements NeighborUpdater {


    @Override
    public void updateNeighbors(BlockPos blockPos, Block block, @Nullable Direction direction) {
        for (Direction direction2 : BlockUpdateUtils.blockUpdateDirections.apply(blockPos)) {
            if (direction2 != direction) {
                this.updateNeighbor(blockPos.offset(direction2), block, blockPos);
            }
        }
    }
}
