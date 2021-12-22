package carpetfixes.helpers;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Longs;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.random.*;

import java.util.Random;

public class XoroshiroCustomRandom extends Random implements AbstractRandom {

    public static final XoroshiroCustomRandom globalRand = new XoroshiroCustomRandom();

    /**
     * A custom Xoroshiro128++ PRNG class that extends from Random & implements AbstractRandom in order to
     * replace the default random generators in the game. While still using Xoroshiro128++
     *
     * This does however mean that it's not as fast as it could be, since Random adds a bunch of overhead
     */

    private static final float FLOAT_MULTIPLIER = 5.9604645E-8F;
    private static final double DOUBLE_MULTIPLIER = 1.1102230246251565E-16D;

    private Xoroshiro128PlusPlusRandomImpl implementation;
    private GaussianGenerator gaussianGenerator = null;

    public XoroshiroCustomRandom() {
        this(RandomSeed.createXoroshiroSeed(RandomSeed.getSeed()));
    }

    public XoroshiroCustomRandom(long seed) {
        this(RandomSeed.createXoroshiroSeed(seed));
    }

    public XoroshiroCustomRandom(RandomSeed.XoroshiroSeed seed) {
        this(seed.seedLo(), seed.seedHi());
    }

    public XoroshiroCustomRandom(long seedLo, long seedHi) {
        this.implementation = new Xoroshiro128PlusPlusRandomImpl(seedLo, seedHi);
    }

    @Override
    public AbstractRandom derive() {
        return new XoroshiroCustomRandom(this.implementation.next(), this.implementation.next());
    }

    @Override
    public RandomDeriver createRandomDeriver() {
        return new RandomDeriver(this.implementation.next(), this.implementation.next());
    }

    @Override
    public void setSeed(long l) {
        this.implementation = new Xoroshiro128PlusPlusRandomImpl(RandomSeed.createXoroshiroSeed(l));
        if (this.gaussianGenerator == null) {
            this.gaussianGenerator = new GaussianGenerator(this);
        }
        this.gaussianGenerator.reset();
    }

    @Override
    public int nextInt() {
        return (int)this.implementation.next();
    }

    @Override
    public int nextInt(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("Bound must be positive");
        } else {
            long l = Integer.toUnsignedLong(this.nextInt());
            long m = l * (long)i;
            long n = m & 4294967295L;
            if (n < (long)i) {
                for(int j = Integer.remainderUnsigned(~i + 1, i); n < (long)j; n = m & 4294967295L) {
                    l = Integer.toUnsignedLong(this.nextInt());
                    m = l * (long)i;
                }
            }

            long j = m >> 32;
            return (int)j;
        }
    }

    @Override
    public long nextLong() {
        return this.implementation.next();
    }

    @Override
    public boolean nextBoolean() {
        return (this.implementation.next() & 1L) != 0L;
    }

    @Override
    public float nextFloat() {
        return (float)this.nextXor(24) * FLOAT_MULTIPLIER;
    }

    @Override
    public double nextDouble() {
        return (double)this.nextXor(53) * DOUBLE_MULTIPLIER;
    }

    @Override
    public double nextGaussian() {
        if (this.gaussianGenerator == null) {
            this.gaussianGenerator = new GaussianGenerator(this);
        }
        return this.gaussianGenerator.next();
    }

    @Override
    public void skip(int count) {
        for(int i = 0; i < count; ++i) {
            this.implementation.next();
        }

    }

    protected long nextXor(int bits) {
        return this.implementation.next() >>> 64 - bits;
    }

    private static long initialScramble(long seed) {return 0L;}

    @Override
    public void nextBytes(byte[] bytes) {
        for (int i = 0, len = bytes.length; i < len; )
            for (int rnd = nextInt(),
                 n = Math.min(len - i, Integer.SIZE/Byte.SIZE);
                 n-- > 0; rnd >>= Byte.SIZE)
                bytes[i++] = (byte)rnd;
    }

    public static class RandomDeriver implements net.minecraft.world.gen.random.RandomDeriver {
        private static final HashFunction MD5_HASHER = Hashing.md5();
        private final long seedLo;
        private final long seedHi;

        public RandomDeriver(long seedLo, long seedHi) {
            this.seedLo = seedLo;
            this.seedHi = seedHi;
        }

        public AbstractRandom createRandom(int x, int y, int z) {
            long l = MathHelper.hashCode(x, y, z);
            long m = l ^ this.seedLo;
            return new XoroshiroCustomRandom(m, this.seedHi);
        }

        public AbstractRandom createRandom(String string) {
            byte[] bs = MD5_HASHER.hashString(string, Charsets.UTF_8).asBytes();
            long l = Longs.fromBytes(bs[0], bs[1], bs[2], bs[3], bs[4], bs[5], bs[6], bs[7]);
            long m = Longs.fromBytes(bs[8], bs[9], bs[10], bs[11], bs[12], bs[13], bs[14], bs[15]);
            return new XoroshiroCustomRandom(l ^ this.seedLo, m ^ this.seedHi);
        }

        @VisibleForTesting
        public void addDebugInfo(StringBuilder info) {
            info.append("seedLo: ").append(this.seedLo).append(", seedHi: ").append(this.seedHi);
        }
    }
}
