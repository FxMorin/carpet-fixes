package carpetfixes.helpers;

import net.minecraft.block.BlockState;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockStateRaycastContext;
import net.minecraft.world.World;

import java.util.function.BiFunction;
import java.util.function.Function;

public class RaycastUtils {

    /**
     * This class is currently only used for occlusion checks against blocks. Due to a bug, although more raycasts
     * will be implemented here in the future. Such as explosions
     */

    private static final double RAY_STEP_START = -1.0E-7D;

    //Raycast from net.minecraft.world.BlockView
    public static BlockHitResult raycast(World world, BlockStateRaycastContext context) {
        return raycast(context.getStart(), context.getEnd(), context, (newContext, pos) -> {
            BlockState blockState = world.getBlockState(pos);
            Vec3d endPos = newContext.getStart().subtract(newContext.getEnd());
            if (newContext.getStatePredicate().test(blockState)) {
                return new BlockHitResult(
                        newContext.getEnd(),
                        Direction.getFacing(endPos.x, endPos.y, endPos.z),
                        new BlockPos(newContext.getEnd()),
                        false
                );
            }
            return null;
        }, (newContext) -> {
            Vec3d endPos = newContext.getStart().subtract(newContext.getEnd());
            return BlockHitResult.createMissed(
                    newContext.getEnd(),
                    Direction.getFacing(endPos.x, endPos.y, endPos.z),
                    new BlockPos(newContext.getEnd())
            );
        });
    }

    //Raycast from net.minecraft.world.BlockView //TODO: Fix possible issue in 45 degree calculation
    private static <T, C> T raycast(Vec3d start, Vec3d end, C context, BiFunction<C, BlockPos, T> blockHitFactory, Function<C, T> missFactory) {
        if (start.equals(end)) return missFactory.apply(context);
        double d = MathHelper.lerp(RAY_STEP_START, end.x, start.x);
        double e = MathHelper.lerp(RAY_STEP_START, end.y, start.y);
        double f = MathHelper.lerp(RAY_STEP_START, end.z, start.z);
        double g = MathHelper.lerp(RAY_STEP_START, start.x, end.x);
        double h = MathHelper.lerp(RAY_STEP_START, start.y, end.y);
        double i = MathHelper.lerp(RAY_STEP_START, start.z, end.z);
        int j = MathHelper.floor(g);
        int k = MathHelper.floor(h);
        int l = MathHelper.floor(i);
        int x1 = Math.abs(j);
        int y1 = Math.abs(k);
        int z1 = Math.abs(l);
        BlockPos.Mutable mutable = new BlockPos.Mutable(j, k, l);
        T object = blockHitFactory.apply(context, mutable);
        if (object != null) return object;
        boolean isPerfectHorizontalDiagonal = x1 == z1; //This is a 45 degree angle
        boolean isPerfectVerticalDiagonalX = x1 == y1;
        boolean isPerfectVerticalDiagonalZ = y1 == z1;
        double m = d - g;
        double n = e - h;
        double o = f - i;
        int p = MathHelper.sign(m);
        int q = MathHelper.sign(n);
        int r = MathHelper.sign(o);
        double s = p == 0 ? Double.MAX_VALUE : (double)p / m;
        double t = q == 0 ? Double.MAX_VALUE : (double)q / n;
        double u = r == 0 ? Double.MAX_VALUE : (double)r / o;
        double v = s * (p > 0 ? 1.0D - MathHelper.fractionalPart(g) : MathHelper.fractionalPart(g));
        double w = t * (q > 0 ? 1.0D - MathHelper.fractionalPart(h) : MathHelper.fractionalPart(h));
        double x = u * (r > 0 ? 1.0D - MathHelper.fractionalPart(i) : MathHelper.fractionalPart(i));
        while(v <= 1.0 || w <= 1.0 || x <= 1.0) {
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
            T object2 = blockHitFactory.apply(context, mutable.set(j, k, l));
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
            if (object2 != null) return object2;
        }
        return missFactory.apply(context);
    }
}
