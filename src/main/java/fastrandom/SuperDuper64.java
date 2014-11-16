package fastrandom;

import java.io.Serializable;

/** 
 * A combination of a linear congruential generator and linear feedback shift
 * register with a period of approximately 2<sup>128</sup>. See the following
 * post by Marsaglia:
 * <p>
 * http://www.science-bbs.com/59-crypt-random-numbers/4c030612b8501e13.htm
 */
public class SuperDuper64 extends AbstractFastRandom implements FastRandom, Serializable {
    private static final long serialVersionUID = 7821100332391741084L;
    
    private long x, y;

    /** Constructs a random number generator. */
    public SuperDuper64() {
        this(SeedGenerator.getSeed());
    }

    /** Constructs a random number generator with the specified seed. */
    public SuperDuper64(long seed) {
        setSeed(seed);
    }

    @Override
    public void setSeed(long seed) {
        x = seed;
        y = SeedGenerator.LCG(x);
        if (y == 0L) {
            y = -1L; // The generator will degrade into an LCG if y is 0.
        }
        clearGaussian();
    }
       
    @Override
    public long nextLong() {
        x = x * 6906969069L + 1234567L;
        y ^= y << 13;
        y ^= y >>> 17;
        y ^= y << 43;
        return x + y;
    }
    
    @Override
    public double nextDouble() {
        return (nextLong() >>> 11) / (double)(1L << 53);
    }
    
    @Override
    public void nextBytes(byte[] bytes) {
        int i = 0;
        int end = bytes.length - 7;
        while (i < end) {
            long rnd = nextLong();
            bytes[i] = (byte)rnd;
            bytes[i + 1] = (byte)(rnd >> 8);
            bytes[i + 2] = (byte)(rnd >> 16);
            bytes[i + 3] = (byte)(rnd >> 24);
            bytes[i + 4] = (byte)(rnd >> 32);
            bytes[i + 5] = (byte)(rnd >> 40);
            bytes[i + 6] = (byte)(rnd >> 48);
            bytes[i + 7] = (byte)(rnd >> 56);
            i += 8;
        }
        if (i < bytes.length) {
            long rnd = nextLong();
            do {
                bytes[i++] = (byte)rnd;
                rnd >>= 8;
            } while (i < bytes.length);
        }
    }

    @Override
    protected int next(int bits) {
        return (int)(nextLong() >>> (64 - bits));
    }
}
