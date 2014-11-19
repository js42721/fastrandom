package fastrandom;

import java.io.Serializable;

/** 
 * KISS (Keep It Simple, Stupid) is a combination of several simple generators.
 * This class implements the 2007 version which uses no multiplication and has a
 * period of approximately 2<sup>123</sup>. See the following post by Marsaglia
 * for more information:
 * <p>
 * http://compgroups.net/comp.lang.fortran/fortran-and-c-united-with-a-kiss/599414
 */
public class KISS extends AbstractFastRandom implements FastRandom, Serializable {
    private static final long serialVersionUID = -8639916730566814187L;
    
    private int x, y, z, w, c;

    /** Constructs a random number generator. */
    public KISS() {
        this(SeedGenerator.getSeed());
    }

    /** Constructs a random number generator with the specified seed. */
    public KISS(long seed) {
        setSeed(seed);
    }

    @Override
    public void setSeed(long seed) {
        clearGaussian();
        /*
         * x: any 32-bit integer
         * y: nonzero 32-bit integer
         * z, w: 31-bit integers not multiples of 7559
         * c: 0 or 1
         */
        x = (int)(seed >> 32);
        y = (int)seed;
        if (y == 0) {
            y = -1;
        }
        z = x & 0x7fffffff;
        if (z % 7559 == 0) {
            ++z;
        }
        w = y & 0x7fffffff;
        if (w % 7559 == 0) {
            ++w;
        }
        c = y >>> 31;
    }
    
    @Override
    protected int next(int bits) {
        x += 545925293;
        y ^= y << 13;
        y ^= y >>> 17;
        y ^= y << 5;
        int t = z + w + c;
        z = w;
        c = t >>> 31;
        w = t & 0x7fffffff;
        return (x + y + w) >>> (32 - bits);
    }
}
