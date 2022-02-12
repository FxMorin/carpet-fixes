package carpetfixes.helpers;

import carpetfixes.CarpetFixesSettings;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class BlockUpdateUtils {

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

    //TODO: rename redstone torch rule to be more generalized (if any other locations are found)
    //TODO: Severely needs optimization xD
    public static void doExtendedBlockUpdates(World world, BlockPos pos, Block block, boolean removedAndEmitsPower, boolean requiresASelfBlockUpdate) {
        Set<BlockPos> blockPosList = null;
        if (CarpetFixesSettings.duplicateBlockUpdatesFix) blockPosList = new HashSet<>();
        Direction[] extendedDirections = CarpetFixesSettings.extendedBlockUpdateOrderFix ? DirectionUtils.directions :
                (CarpetFixesSettings.parityRandomBlockUpdates ? DirectionUtils.randomDirectionArray(pos) :
                        Direction.values());
        Direction[] directions = CarpetFixesSettings.blockUpdateOrderFix ? DirectionUtils.directions :
                (CarpetFixesSettings.parityRandomBlockUpdates ? DirectionUtils.randomDirectionArray(pos) :
                        DirectionUtils.updateDirections);
        if (CarpetFixesSettings.redstoneTorchOrderOnBreakFix && removedAndEmitsPower) {
            for(int dirNum = 0; dirNum < 6; ++dirNum) { //Do Updates around block torch first. Preventing wrong order
                world.updateNeighbor(pos.offset(directions[dirNum]), block, pos);
            }
        }
        for(Direction dir : extendedDirections) {
            BlockPos p = pos.offset(dir);
            for(int dirNum = 0; dirNum < 6; ++dirNum) {
                BlockPos nextPos = p.offset(directions[dirNum]);
                if (!CarpetFixesSettings.uselessSelfBlockUpdateFix || requiresASelfBlockUpdate || !nextPos.equals(pos)) {
                    if (!CarpetFixesSettings.duplicateBlockUpdatesFix || !blockPosList.contains(nextPos)) {
                        requiresASelfBlockUpdate = false;
                        world.updateNeighbor(nextPos, block, p);
                        if (CarpetFixesSettings.duplicateBlockUpdatesFix) blockPosList.add(nextPos);
                    }
                }
            }
        }
    }

    public static boolean canUpdateNeighborsAlwaysWithOrder(World world, BlockPos p, Block block) {
        if (CarpetFixesSettings.blockUpdateOrderFix) {
            for(Direction d : DirectionUtils.directions) world.updateNeighbor(p.offset(d), block, p);
            return true;
        } else if (CarpetFixesSettings.parityRandomBlockUpdates) {
            for(Direction d : DirectionUtils.randomDirectionArray(p)) world.updateNeighbor(p.offset(d), block, p);
            return true;
        }
        return false;
    }

    public static boolean canUpdateNeighborsExceptWithOrder(World world, BlockPos pos, Block block, Direction direction) {
        if (CarpetFixesSettings.blockUpdateOrderFix) {
            for (Direction d : DirectionUtils.directions) {
                if (direction != d) world.updateNeighbor(pos.offset(d), block, pos);
            }
            return true;
        } else if (CarpetFixesSettings.parityRandomBlockUpdates) {
            for (Direction d : DirectionUtils.randomDirectionArray(pos)) {
                if (direction != d) world.updateNeighbor(pos.offset(d), block, pos);
            }
            return true;
        }
        return false;
    }
}
