package carpetfixes.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.DyeColor;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockStateRaycastContext;
import net.minecraft.world.World;

import java.util.function.BiFunction;
import java.util.function.Function;

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

    //Raycast from BlockView
    public static BlockHitResult raycast(World world, BlockStateRaycastContext context) {
        return raycast(context.getStart(), context.getEnd(), context, (contextx, pos) -> {
            BlockState blockState = world.getBlockState(pos);
            Vec3d vec3d = contextx.getStart().subtract(contextx.getEnd());
            return contextx.getStatePredicate().test(blockState) ? new BlockHitResult(contextx.getEnd(), Direction.getFacing(vec3d.x, vec3d.y, vec3d.z), new BlockPos(contextx.getEnd()), false) : null;
        }, (contextx) -> {
            Vec3d vec3d = contextx.getStart().subtract(contextx.getEnd());
            return BlockHitResult.createMissed(contextx.getEnd(), Direction.getFacing(vec3d.x, vec3d.y, vec3d.z), new BlockPos(contextx.getEnd()));
        });
    }

    //Raycast from BlockView //TODO: Fix possible issue in 45 degree calculation
    private static <T, C> T raycast(Vec3d start, Vec3d end, C context, BiFunction<C, BlockPos, T> blockHitFactory, Function<C, T> missFactory) {
        if (start.equals(end)) {
            return missFactory.apply(context);
        } else {
            double d = MathHelper.lerp(-1.0E-7D, end.x, start.x);
            double e = MathHelper.lerp(-1.0E-7D, end.y, start.y);
            double f = MathHelper.lerp(-1.0E-7D, end.z, start.z);
            double g = MathHelper.lerp(-1.0E-7D, start.x, end.x);
            double h = MathHelper.lerp(-1.0E-7D, start.y, end.y);
            double i = MathHelper.lerp(-1.0E-7D, start.z, end.z);
            int j = MathHelper.floor(g);
            int k = MathHelper.floor(h);
            int l = MathHelper.floor(i);
            int x1 = Math.abs(j);
            int y1 = Math.abs(k);
            int z1 = Math.abs(l);
            boolean isPerfectHorizontalDiagonal = x1 == z1; //This is a 45 degree angle
            boolean isPerfectVerticalDiagonalX = x1 == y1;
            boolean isPerfectVerticalDiagonalZ = y1 == z1;
            BlockPos.Mutable mutable = new BlockPos.Mutable(j, k, l);
            T object = blockHitFactory.apply(context, mutable);
            if (object != null) {
                return object;
            } else {
                double m = d - g;
                double n = e - h;
                double o = f - i;
                int p = MathHelper.sign(m);
                int q = MathHelper.sign(n);
                int r = MathHelper.sign(o);
                double s = p == 0 ? 1.7976931348623157E308D : (double)p / m;
                double t = q == 0 ? 1.7976931348623157E308D : (double)q / n;
                double u = r == 0 ? 1.7976931348623157E308D : (double)r / o;
                double v = s * (p > 0 ? 1.0D - MathHelper.fractionalPart(g) : MathHelper.fractionalPart(g));
                double w = t * (q > 0 ? 1.0D - MathHelper.fractionalPart(h) : MathHelper.fractionalPart(h));
                double x = u * (r > 0 ? 1.0D - MathHelper.fractionalPart(i) : MathHelper.fractionalPart(i));
                Object object2;
                do {
                    if (!(v <= 1.0D) && !(w <= 1.0D) && !(x <= 1.0D)) {
                        return missFactory.apply(context);
                    }
                    if (v < w) {
                        if (v < x) {
                            j += p;
                            v += s;
                        } else {
                            l += r;
                            x += u;
                        }
                    } else if (w < x) {
                        k += q;
                        w += t;
                    } else {
                        l += r;
                        x += u;
                    }
                    object2 = blockHitFactory.apply(context, mutable.set(j, k, l));
                    if (isPerfectHorizontalDiagonal && object2 == null) {
                        object2 = blockHitFactory.apply(context, mutable.set(l, k, j));
                    } else {
                        if (isPerfectVerticalDiagonalX && object2 == null) {
                            object2 = blockHitFactory.apply(context, mutable.set(k, j, l));
                        }
                        if (isPerfectVerticalDiagonalZ && object2 == null) {
                            object2 = blockHitFactory.apply(context, mutable.set(j, l, k));
                        }
                    }
                } while(object2 == null);
                return (T) object2;
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
