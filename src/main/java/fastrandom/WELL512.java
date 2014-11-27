package fastrandom;

import java.io.Serializable;

/** 
 * The smallest of the WELL (Well Equidistributed Long-period Linear) series,
 * this generator has a state size of 512 bits and a period of 2<sup>512</sup> - 1.
 * See "Improved Long-Period Generators Based on Linear Recurrences Modulo 2"
 * by L'Ecuyer, Matsumoto, and Panneton.
 */
public class WELL512 extends AbstractFastRandom implements FastRandom, Serializable {
    private static final long serialVersionUID = -178962713019714243L;
    
    private final int[] state;
    private int index;
    
    /** Constructs a random number generator. */
    public WELL512() {
        this(SeedGenerator.getSeed());
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
        state[0] = (int)(seed >> 32);
        state[1] = (int)seed;
        long next = seed;
        for (int i = 2; i < state.length; ++i) {
            next = SeedGenerator.LCG(next);
            state[i] = (int)(next >> 32);
        }
    }
    
    @Override
    protected int next(int bits) {
        int z0 = state[index];
        int z1 = state[(index + 13) & 0xf];
        int z2 = (z0 ^ (z0 << 16)) ^ (z1 ^ (z1 << 15));
        z0 = state[(index + 9) & 0xf];
        z1 = z0 ^ (z0 >>> 11);
        z0 = state[index] = z1 ^ z2;
        index = (index + 15) & 0xf;
        int z3 = state[index];
        int z4 = (z3 ^ (z3 << 2))
               ^ (z2 ^ (z2 << 18))
               ^ (z1 << 28) 
               ^ (z0 ^ ((z0 << 5) & 0xda442d24));
        state[index] = z4;
        return z4 >>> (32 - bits);
    }
}
