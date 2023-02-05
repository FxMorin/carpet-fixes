package carpetfixes.mixins.reIntroduced;

import carpet.CarpetSettings;
import carpet.fakes.LevelInterface;
import carpet.mixins.CollectingNeighborUpdaterAccessor;
import carpetfixes.CarpetFixesServer;
import carpetfixes.helpers.MemEfficientNeighborUpdater;
import carpetfixes.helpers.UpdateSuppressionException;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.block.NeighborUpdater;
import net.minecraft.world.block.SimpleNeighborUpdater;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BarrierBlock.class, priority = 1001)
public class BarrierBlock_instantBlockUpdatesFix extends Block {

    public BarrierBlock_instantBlockUpdatesFix(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public void neighborUpdate(BlockState state, World level, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (CarpetSettings.updateSuppressionBlock != -1) {
            if (fromPos.equals(pos.up())) {
                BlockState stateAbove = level.getBlockState(fromPos);
                if (stateAbove.isOf(Blocks.ACTIVATOR_RAIL) && !stateAbove.get(PoweredRailBlock.POWERED)) {
                    level.scheduleBlockTick(pos, this, 1);
                    NeighborUpdater updater = ((LevelInterface) level).getNeighborUpdater();
                    if (updater instanceof CollectingNeighborUpdaterAccessor cnua)
                        cnua.setCount(cnua.getMaxChainedNeighborUpdates() - CarpetSettings.updateSuppressionBlock);
                    if (updater instanceof SimpleNeighborUpdater ||
                            updater instanceof MemEfficientNeighborUpdater) {
                        throw new UpdateSuppressionException("Update Suppression Block");
                    }

                }
            }
        }
        super.neighborUpdate(state, level, pos, block, fromPos, notify);
    }
}
