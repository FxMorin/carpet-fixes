package carpetfixes.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import static net.minecraft.block.HorizontalFacingBlock.FACING;

public class Utils {

    public static boolean isInModifiableLimit(World world, BlockPos pos) {
        return !world.isOutOfHeightLimit(pos) && world.getWorldBorder().contains(pos);
    }

    public static void updateComparatorsRespectFacing(World world, BlockPos fromPos, Block block) {
        for (Direction dir : Direction.Type.HORIZONTAL) {
            BlockPos pos = fromPos.offset(dir);
            if (!world.isChunkLoaded(pos)) continue;
            BlockState state = world.getBlockState(pos);
            if (state.isOf(Blocks.COMPARATOR)) {
                if (state.get(FACING) == dir.getOpposite()) state.neighborUpdate(world, pos, block, fromPos, false);
            } else if (state.isSolidBlock(world, pos)) {
                pos = pos.offset(dir);
                state = world.getBlockState(pos);
                if (!state.isOf(Blocks.COMPARATOR)) continue;
                if (state.get(FACING) == dir.getOpposite()) state.neighborUpdate(world, pos, block, fromPos, false);
            }
        }
    }

    public static void giveShapeUpdate(World world, BlockState state, BlockPos pos, BlockPos fromPos, Direction direction) {
        BlockState oldState = world.getBlockState(pos);
        Block.replace(oldState, oldState.getStateForNeighborUpdate(direction.getOpposite(), state, world, pos, fromPos), world, pos, Block.NOTIFY_LISTENERS & -34, 0);
    }

    //If I was actually implementing this, the color values would have been binary in order for fast calculations.
    //Never do this in a production build, although this is better than using the RecipeManager xD
    public static DyeColor properDyeMixin(DyeColor col1, DyeColor col2) {
        if (col1.equals(col2)) return col1;
        switch(col1) {
            case WHITE -> {
                switch(col2) {
                    case BLUE -> {return DyeColor.LIGHT_BLUE;}
                    case GRAY -> {return DyeColor.LIGHT_GRAY;}
                    case BLACK -> {return DyeColor.GRAY;}
                    case GREEN -> {return DyeColor.LIME;}
                    case RED -> {return DyeColor.PINK;}
                }
            }
            case BLUE -> {
                switch(col2) {
                    case WHITE -> {return DyeColor.LIGHT_BLUE;}
                    case GREEN -> {return DyeColor.CYAN;}
                    case RED -> {return DyeColor.PURPLE;}
                }
            }
            case RED -> {
                switch(col2) {
                    case YELLOW -> {return DyeColor.ORANGE;}
                    case WHITE -> {return DyeColor.PINK;}
                    case BLUE -> {return DyeColor.PURPLE;}
                }
            }
            case GREEN -> {
                switch(col2) {
                    case BLUE -> {return DyeColor.CYAN;}
                    case WHITE -> {return DyeColor.LIME;}
                }
            }
            case YELLOW -> {if (col2.equals(DyeColor.RED)) return DyeColor.ORANGE;}
            case PURPLE -> {if (col2.equals(DyeColor.PINK)) return DyeColor.MAGENTA;}
            case PINK -> {if (col2.equals(DyeColor.PURPLE)) return DyeColor.MAGENTA;}
            case GRAY -> {if (col2.equals(DyeColor.WHITE)) return DyeColor.LIGHT_GRAY;}
            case BLACK -> {if (col2.equals(DyeColor.WHITE)) return DyeColor.GRAY;}
        }
        return null;
    }
}
