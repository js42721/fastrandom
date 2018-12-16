package fastrandom;

import java.io.Serializable;

/**
 * The 512-bit version of the WELL (Well Equidistributed Long-period Linear)
 * generator. Its period is 2<sup>512</sup> - 1. See "Improved Long-Period
 * Generators Based on Linear Recurrences Modulo 2" by L'Ecuyer, Matsumoto, and
 * Panneton.
 */
public class WELL512a extends AbstractFastRandom implements FastRandom, Serializable {
    private static final long serialVersionUID = -178962713019714243L;

    private static final int R    = 16;
    private static final int M1   = 13;
    private static final int M2   = 9;
    private static final int MASK = 0xf;

    private final int[] s;
    private int si;

    /** Constructs a random number generator. */
    public WELL512a() {
        this(Utils.getSeed());
    }

    /** Constructs a random number generator with the specified seed. */
    public WELL512a(long seed) {
        s = new int[R];
        setSeed(seed);
    }

    @Override
    public void setSeed(long seed) {
        clearGaussian();
        si = 0;
        /* At least one element in the state array must be nonzero. */
        s[0] = (int) (seed >> 32);
        s[1] = (int) seed;
        long next = seed;
        for (int i = 2; i < R; ++i) {
            next = Utils.LCG(next);
            s[i] = (int) (next >> 32);
        }
    }

    @Override
    protected int next(int bits) {
        int v0  = s[si];
        int vm1 = s[(si + M1) & MASK];
        int vm2 = s[(si + M2) & MASK];
        int z0  = s[(si + 15) & MASK];
        int z1  = mat0neg(-16, v0) ^ mat0neg(-15, vm1);
        int z2  = mat0pos(11, vm2);
        int nv1 = z1 ^ z2;
        s[si]   = nv1;
        int res = mat0neg(-2, z0) ^ mat0neg(-18, z1) ^ mat3neg(-28, z2) ^ mat4neg(-5, 0xda442d24, nv1);
        si      = (si + 15) & MASK;
        s[si]   = res;
        return res >>> (32 - bits);
    }

    private static int mat0pos(int t, int v) {
        return v ^ (v >>> t);
    }

    private static int mat0neg(int t, int v) {
        return v ^ (v << -t);
    }

    private static int mat3neg(int t, int v) {
        return v << -t;
    }

    private static int mat4neg(int t, int b, int v) {
        return v ^ ((v << -t) & b);
    }
}