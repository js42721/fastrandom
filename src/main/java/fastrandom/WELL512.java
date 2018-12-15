package fastrandom;

import java.io.Serializable;

/**
 * The 512-bit version of the WELL (Well Equidistributed Long-period Linear)
 * generator. Its period is ~2<sup>512</sup> - 1. See "Improved Long-Period
 * Generators Based on Linear Recurrences Modulo 2" by L'Ecuyer, Matsumoto, and
 * Panneton.
 */
public class WELL512 extends AbstractFastRandom implements FastRandom, Serializable {
    private static final long serialVersionUID = -178962713019714243L;

    private final int[] state;
    private int index;

    /** Constructs a random number generator. */
    public WELL512() {
        this(Utils.getSeed());
    }

    /** Constructs a random number generator with the specified seed. */
    public WELL512(long seed) {
        state = new int[16];
        setSeed(seed);
    }

    @Override
    public void setSeed(long seed) {
        clearGaussian();
        index = 0;
        /* At least one element in the state array must be nonzero. */
        state[0] = (int) (seed >> 32);
        state[1] = (int) seed;
        long next = seed;
        for (int i = 2; i < state.length; ++i) {
            next = Utils.LCG(next);
            state[i] = (int) (next >> 32);
        }
    }

    @Override
    protected int next(int bits) {
        int z1 = state[index];
        int z2 = state[(index + 13) & 0xf];
        int z3 = (z1 ^ (z1 << 16)) ^ (z2 ^ (z2 << 15));
        z1 = state[(index + 9) & 0xf];
        z2 = z1 ^ (z1 >>> 11);
        z1 = state[index] = z2 ^ z3;
        index = (index + 15) & 0xf;
        int z4 = state[index];
        int z5 = (z4 ^ (z4 << 2))
               ^ (z3 ^ (z3 << 18))
               ^ (z2 << 28)
               ^ (z1 ^ ((z1 << 5) & 0xda442d24));
        state[index] = z5;
        return z5 >>> (32 - bits);
    }
}
