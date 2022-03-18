package carpetfixes.helpers;

import carpetfixes.CFSettings;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static net.minecraft.class_7165.field_37839;

public class BlockUpdateUtils {

    public static Function<BlockPos,Direction[]> blockUpdateDirections = (b) -> field_37839;

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
        //Set the direction update order
        Direction[] directions = CFSettings.blockUpdateOrderFix ?
                DirectionUtils.directions :
                CFSettings.parityRandomBlockUpdates ?
                        DirectionUtils.randomDirectionArray(pos) :
                        field_37839;
        //If redstone component should update blocks closer to itself before giving extended block updates
        if (CFSettings.redstoneComponentUpdateOrderOnBreakFix && removedAndEmitsPower) {
            for(Direction dir : directions) { //Do block updates around block first. Preventing wrong order
                world.updateNeighbor(pos.offset(dir), block, pos);
            }
        }
        for(Direction extendedDir : extendedDirections) { //For each extended block update direction
            BlockPos p = pos.offset(extendedDir);
            for(Direction dir : directions) { //For each block update direction
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
}
