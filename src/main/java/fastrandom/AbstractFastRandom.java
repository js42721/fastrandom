package fastrandom;

/** Partial implementation of {@link FastRandom}. */
public abstract class AbstractFastRandom implements FastRandom {
    private boolean hasNextGaussian;
    private double nextGaussian;

    /** Supplies random bits to all the generation methods. */
    protected abstract int next(int bits);

    @Override
    public void nextBytes(byte[] bytes) {
        int i = 0;
        int end = bytes.length - 3;
        while (i < end) {
            int rnd = next(32);
            bytes[i] = (byte) rnd;
            bytes[i + 1] = (byte) (rnd >> 8);
            bytes[i + 2] = (byte) (rnd >> 16);
            bytes[i + 3] = (byte) (rnd >> 24);
            i += 4;
        }
        if (i < bytes.length) {
            int rnd = next(32);
            do {
                bytes[i++] = (byte) rnd;
                rnd >>= 8;
            } while (i < bytes.length);
        }
    }

    @Override
    public int nextInt() {
        return next(32);
    }

    @Override
    public int nextInt(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        if ((n & (n - 1)) == 0) { // n is a power of 2 for n > 0.
            /*
             * Returns the high bits rather than the low bits. This is helpful
             * for generators whose output exhibits less entropy in the lower
             * bits.
             */
            return (int) ((n * (long) next(31)) >> 31);
        }
        /*
         * Performs rejection sampling to achieve a uniform distribution if n
         * does not divide the range of nonnegative int values.
         */
        int rnd, res;
        do {
            rnd = next(31);
            res = rnd % n;
        } while (rnd - res + (n - 1) < 0);
        return res;
    }

    @Override
    public long nextLong() {
        return ((long) next(32) << 32) + next(32);
    }

    @Override
    public boolean nextBoolean() {
        return next(1) != 0;
    }

    @Override
    public float nextFloat() {
        return next(24) / (float) (1 << 24);
    }

    @Override
    public double nextDouble() {
        return (((long) next(26) << 27) + next(27)) / (double) (1L << 53);
    }

    @Override
    public double nextGaussian() {
        if (hasNextGaussian) {
            hasNextGaussian = false;
            return nextGaussian;
        }
        /* Marsaglia's polar method. */
        double x, y, s;
        do {
            x = 2 * nextDouble() - 1;
            y = 2 * nextDouble() - 1;
            s = x * x + y * y;
        } while (s >= 1 || s == 0);
        double r = StrictMath.sqrt(-2 * StrictMath.log(s) / s);
        nextGaussian = r * y;
        hasNextGaussian = true;
        return r * x;
    }

    /** Call this in {@code setSeed} if {@code nextGaussian} is not overridden. */
    protected void clearGaussian() {
        hasNextGaussian = false;
    }
}