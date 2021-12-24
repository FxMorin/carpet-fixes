package carpetfixes.helpers;

public class FastMath {

    private static final double HYPOT_MAX_MAG = 2^511;
    private static final double HYPOT_FACTOR = 2^750;

    /**
     * @author FX - PR0CESS
     * ~1.25x faster than {@link Math#round(float)}
     */
    public static int round(float a) {
        return a > 0F ? (int)(a + .5F) : (int)(a - .5F);
    }

    /**
     * @author FX - PR0CESS
     * ~1.28x faster than {@link Math#round(double)}
     */
    public static long round(double a) {
        return a > 0D ? (long)(a + .5D) : (long)(a - .5D);
    }

    /**
     * @author FX - PR0CESS
     * Hypot implementation from the jafama library. Not 100% accurate! (3E-14%, 15 is perfectly accurate)
     * ~1.6x faster than {@link Math#hypot(double,double)}
     */
    public static double hypot(double x, double y) {
        x = Math.abs(x);
        y = Math.abs(y);
        if (y < x) { // Ensuring x <= y
            final double a = x;
            x = y;
            y = a;
        } else if (!(y >= x)) { // Testing if we have some NaN
            return x == Double.POSITIVE_INFINITY ? Double.POSITIVE_INFINITY : Double.NaN;
        }
        if (y-x == y) { // x too small to subtract from y
            return y;
        } else {
            double factor;
            if (y > HYPOT_MAX_MAG) { // y is too large: scaling down
                x *= (1/HYPOT_FACTOR);
                y *= (1/HYPOT_FACTOR);
                factor = HYPOT_FACTOR;
            } else if (x < (1/HYPOT_MAX_MAG)) { // x is too small: scaling up
                x *= HYPOT_FACTOR;
                y *= HYPOT_FACTOR;
                factor = (1/HYPOT_FACTOR);
            } else {
                factor = 1.0;
            }
            return factor * Math.sqrt(x*x+y*y);
        }
    }
}
