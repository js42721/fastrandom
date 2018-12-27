package fastrandom;

import java.util.concurrent.atomic.AtomicLong;

public final class Utils {
    private static final AtomicLong SEED = new AtomicLong();

    private Utils() {
    }

    /** Returns (ax + b) mod p. */
    public static long lcg(long x) {
        return x * 6364136223846793005L + 1442695040888963407L;
    }

    /** Returns a pseudorandomly generated seed. */
    public static long getSeed() {
        long seed, nextSeed;
        do {
            seed = SEED.get();
            nextSeed = lcg(seed);
        } while (!SEED.compareAndSet(seed, nextSeed));
        return nextSeed ^ System.nanoTime();
    }
}
