package carpetfixes.helpers;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.block.NeighborUpdater;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static net.minecraft.world.block.NeighborUpdater.UPDATE_ORDER;

public class BlockUpdateUtils {

    public static Function<BlockPos,Direction[]> blockUpdateDirections = (b) -> UPDATE_ORDER;

    /**
     *  Default Block Update Order:
     *   ~ [West, East, Down, Up, North, South]
     *   ~ [-x, +x, -y, +y, -z, +z]
     *
     *   Default Extended Block Update Order:
     *   ~ [Down, Up, West, East, North, South]
     *   ~ [-y, +y, -x, +x, -z, +z]
     *
     *   Proper Block Update Order:
     *   ~ [West, East, North, South, Down, Up]
     *   ~ [-x, +x, -z, +z, -y, +y]
     */

    //TODO: Needs optimization
    public static void doExtendedBlockUpdates(World world, BlockPos pos, Block block,
                                              boolean removedAndEmitsPower, boolean requiresASelfBlockUpdate) {
        Set<BlockPos> blockPosList = CFSettings.duplicateBlockUpdatesFix ? new HashSet<>() : null;
        //Set the extended direction update order
        Direction[] extendedDirections = CFSettings.extendedBlockUpdateOrderFix ?
                DirectionUtils.directions :
                CFSettings.parityRandomBlockUpdates ?
                        DirectionUtils.randomDirectionArray(pos) :
                        Direction.values();
        //If redstone component should update blocks closer to itself before giving extended block updates
        if (CFSettings.redstoneComponentUpdateOrderOnBreakFix && removedAndEmitsPower) {
            //Do block updates around block first. Preventing wrong order
            for(Direction dir : BlockUpdateUtils.blockUpdateDirections.apply(pos))
                world.updateNeighbor(pos.offset(dir), block, pos);
        }
        for(Direction extendedDir : extendedDirections) { //For each extended block update direction
            BlockPos p = pos.offset(extendedDir);
            for(Direction dir : BlockUpdateUtils.blockUpdateDirections.apply(p)) { //For each block update direction
                BlockPos nextPos = p.offset(dir);
                if (!CFSettings.uselessSelfBlockUpdateFix || requiresASelfBlockUpdate || !nextPos.equals(pos)) {
                    if (!CFSettings.duplicateBlockUpdatesFix || !blockPosList.contains(nextPos)) {
                        requiresASelfBlockUpdate = false;
                        world.updateNeighbor(nextPos, block, p);
                        if (CFSettings.duplicateBlockUpdatesFix) blockPosList.add(nextPos);
                    }
                }
            }
        }
    }

    /**
     * I use this method since there are multiple places where I need to call neighborUpdate, so we do this from here
     * and add the required checks all at the same place.
     */
    public static void doNeighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (CFSettings.someUpdatesDontCatchExceptionsFix) {
            NeighborUpdater.tryNeighborUpdate(world, state, pos, block, fromPos, notify);
        } else {
            state.neighborUpdate(world, pos, block, fromPos, notify);
        }
    }
}
