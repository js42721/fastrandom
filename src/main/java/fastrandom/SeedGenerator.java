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
}
