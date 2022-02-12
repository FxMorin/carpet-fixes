package carpetfixes;

import carpetfixes.helpers.UpdateScheduler;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class CarpetFixesInit {

    public static boolean scheduleWorldBorderReset = false;

    public static final ThreadLocal<Set<BlockPos>> lastDirt = ThreadLocal.withInitial(HashSet::new);

    public static HashMap<World,UpdateScheduler> updateScheduler = new HashMap<>();

    public static Direction[] directions = new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.DOWN, Direction.UP};

    public static final Predicate<BlockState> IS_REPLACEABLE = (blockState) -> blockState.getMaterial().isReplaceable();
}
