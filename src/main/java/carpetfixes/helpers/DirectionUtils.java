package carpetfixes.helpers;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.random.Xoroshiro128PlusPlusRandom;

public class DirectionUtils {

    public static Direction[] directions = new Direction[]{
            Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.DOWN, Direction.UP
    };

    public static Direction[] horizontal = new Direction[]{
            Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH
    };

    public static Direction[] extendedUpdateDirections = new Direction[]{
            Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST
    };

    private static final Xoroshiro128PlusPlusRandom random = new Xoroshiro128PlusPlusRandom(0);

    public static Direction[] randomDirectionArray(BlockPos pos) {
        random.setSeed(pos.asLong());
        Direction[] array = directions.clone();
        for (int i = array.length-1; i > 0; i--) {
            int rand = random.nextInt(i);
            Direction temp = array[i];
            array[i] = array[rand];
            array[rand] = temp;
        }
        return array;
    }
}
