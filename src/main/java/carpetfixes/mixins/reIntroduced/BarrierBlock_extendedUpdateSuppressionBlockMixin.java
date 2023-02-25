package carpetfixes.mixins.reIntroduced;

import carpet.CarpetSettings;
import carpet.fakes.LevelInterface;
import carpet.mixins.CollectingNeighborUpdaterAccessor;
import carpetfixes.helpers.MemEfficientNeighborUpdater;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.block.NeighborUpdater;
import net.minecraft.world.block.SimpleNeighborUpdater;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BarrierBlock.class, priority = 1001)
public class BarrierBlock_extendedUpdateSuppressionBlockMixin extends Block {

    public BarrierBlock_extendedUpdateSuppressionBlockMixin(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public void neighborUpdate(BlockState state, World level, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (CarpetSettings.updateSuppressionBlock != -1) {
            if (fromPos.equals(pos.up())) {
                BlockState stateAbove = level.getBlockState(fromPos);
                if (stateAbove.isOf(Blocks.ACTIVATOR_RAIL) && !stateAbove.get(PoweredRailBlock.POWERED)) {
                    NeighborUpdater updater = ((LevelInterface) level).getNeighborUpdater();
                    if (updater instanceof CollectingNeighborUpdaterAccessor cnua) {
                        level.scheduleBlockTick(pos, this, 1);
                        cnua.setCount(cnua.getMaxChainedNeighborUpdates() - CarpetSettings.updateSuppressionBlock);
                    } else if (updater instanceof MemEfficientNeighborUpdater menu) {
                        level.scheduleBlockTick(pos, this, 1);
                        menu.setDepth(menu.getMaxSize() - CarpetSettings.updateSuppressionBlock);
                    } else if (updater instanceof SimpleNeighborUpdater) {
                        if (CarpetSettings.updateSuppressionBlock > 0) {
                            level.scheduleBlockTick(pos, this, CarpetSettings.updateSuppressionBlock);
                        }
                        throw new StackOverflowError("updateSuppressionBlock");
                    }
                }
            }
        }
        super.neighborUpdate(state, level, pos, block, fromPos, notify);
    }
}
