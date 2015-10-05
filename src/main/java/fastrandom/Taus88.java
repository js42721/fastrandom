package fastrandom;

import java.io.Serializable;

/**
 * A combined linear feedback shift register with a period of approximately
 * 2<sup>88</sup>. Adapted from code found in "Maximally Equidistributed
 * Combined Tausworthe Generators" by L'Ecuyer.
 */
public class Taus88 extends AbstractFastRandom implements FastRandom, Serializable {
    private static final long serialVersionUID = 3042624850227558320L;

    private int s1, s2, s3;

    /** Constructs a random number generator. */
    public Taus88() {
        this(SeedGenerator.getSeed());
    }

    /** Constructs a random number generator with the specified seed. */
    public Taus88(long seed) {
        setSeed(seed);
    }

    @Override
    public void setSeed(long seed) {
        clearGaussian();
        /* Upper 31 bits must not be all zero. */
        s1 = (int) seed;
        if ((s1 & 0xffffffffL) < 2L) {
            s1 += 2;
        }
        /* Upper 29 bits must not be all zero. */
        s2 = (int) (seed >> 32);
        if ((s2 & 0xffffffffL) < 8L) {
            s2 += 8;
        }
        /* Upper 28 bits must not be all zero. */
        s3 = (int) (SeedGenerator.LCG(seed) >> 32);
        if ((s3 & 0xffffffffL) < 16L) {
            s3 += 16;
        }
    }

    @Override
    protected int next(int bits) {
        s1 = ((s1 &  -2) << 12) ^ (((s1 << 13) ^ s1) >>> 19);
        s2 = ((s2 &  -8) <<  4) ^ (((s2 <<  2) ^ s2) >>> 25);
        s3 = ((s3 & -16) << 17) ^ (((s3 <<  3) ^ s3) >>> 11);
        return (s1 ^ s2 ^ s3) >>> (32 - bits);
    }
}
