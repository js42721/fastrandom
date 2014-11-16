package fastrandom;

import java.io.Serializable;

import fastrandom.AbstractFastRandom;
import fastrandom.SeedGenerator;

/** 
 * Lagged Fibonacci generator with a period of approximately 2<sup>287</sup>.
 * See the following post by Marsaglia:
 * <p>
 * http://groups.google.com/group/sci.crypt/msg/eb4ddde782b17051
 */
public class LFib4 extends AbstractFastRandom implements FastRandom, Serializable {
    private static final long serialVersionUID = 7617532577886824590L;
    
    private final int[] state;
    private int index;

    /** Constructs a random number generator. */
    public LFib4() {
        this(SeedGenerator.getSeed());
    }

    /** Constructs a random number generator with the specified seed. */
    public LFib4(long seed) {
        state = new int[256];
        setSeed(seed);
    }
    
    @Override
    public void setSeed(long seed) {
        /* At least one of the values in the state array must be odd. */
        SeedGenerator.expand(seed, state);
        index = 0;
        clearGaussian();
    }

    @Override
    protected int next(int bits) {
        index = (index + 1) & 0xff;
        state[index] += state[(index +  58) & 0xff]
                      + state[(index + 119) & 0xff]
                      + state[(index + 178) & 0xff];
        return state[index] >>> (32 - bits);
    }
}
