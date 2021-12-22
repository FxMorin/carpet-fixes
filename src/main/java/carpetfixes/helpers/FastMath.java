package carpetfixes.helpers;

public class FastMath {

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
}
