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
        s1 = (int)seed;
        if ((s1 & 0xffffffffL) < 2L) {
            s1 = ~s1; // Upper 31 bits must be nonzero.
        }
        seed = SeedGenerator.LCG(seed);
        s2 = (int)(seed >> 32);
        if ((s2 & 0xffffffffL) < 8L) {
            s2 = ~s2; // Upper 29 bits must be nonzero.
        }
        seed = SeedGenerator.LCG(seed);
        s3 = (int)(seed >> 32);
        if ((s3 & 0xffffffffL) < 16L) {
            s3 = ~s3; // Upper 28 bits must be nonzero.
        }
        clearGaussian();
    }

    @Override
    protected int next(int bits) {
        int b;
        b  = ((s1 << 13) ^  s1) >>> 19;
        s1 = ((s1 &  -2) << 12) ^ b;
        b  = ((s2 <<  2) ^  s2) >>> 25;
        s2 = ((s2 &  -8) <<  4) ^ b;
        b  = ((s3 <<  3) ^  s3) >>> 11;
        s3 = ((s3 & -16) << 17) ^ b;
        return (s1 ^ s2 ^  s3) >>> (32 - bits);
    }
}
