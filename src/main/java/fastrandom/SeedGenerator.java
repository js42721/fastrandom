package fastrandom;

import java.util.concurrent.atomic.AtomicLong;

/** Used to seed non-cryptographic PRNGs. */
public final class SeedGenerator {
    private static final AtomicLong SEED = new AtomicLong();
    
    private SeedGenerator() {
    }
    
    /** Returns a pseudorandomly generated seed. */
    public static long getSeed() {
        long seed, nextSeed;
        do {
            seed = SEED.get();
            nextSeed = LCG(seed);
        } while (!SEED.compareAndSet(seed, nextSeed));
        return nextSeed ^ System.nanoTime();
    }
    
    /** Returns (ax + b) mod p. */
    public static long LCG(long x) {
        return x * 6364136223846793005L + 1442695040888963407L;
    }
    
    /**
     * Copies a {@code long} seed into an {@code int} array and fills any
     * remaining space with pseudorandom bits generated from the seed. If the
     * array has a size of one, it will receive the upper 32 bits of the seed.
     * 
     * @throws NullPointerException if array is null
     */
    public static void expand(long seed, int[] array) {
        if (array.length == 0) {
            return;
        }
        array[0] = (int)(seed >> 32);
        if (array.length > 1) {
            array[1] = (int)seed;
            for (int i = 2; i < array.length; ++i) {
                seed = LCG(seed);
                array[i] = (int)(seed >> 32);
            }
        }
    }
}
