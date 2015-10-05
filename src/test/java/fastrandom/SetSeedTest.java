package fastrandom;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SetSeedTest {
    @Parameters
    public static Iterable<Object[]> getAll() {
        return Arrays.asList(new Object[][] {
                { KISS.class },
                { MersenneTwister.class },
                { Taus88.class },
                { WELL512.class }
        });
    }

    private Class<? extends FastRandom> c;

    public SetSeedTest(Class<? extends FastRandom> c) {
        this.c = c;
    }

    @Test
    public void testSetSeed() throws Exception {
        long seed = 9876543210L;

        FastRandom rnd = c.getConstructor(Long.TYPE).newInstance(seed);
        double r1 = rnd.nextGaussian();

        rnd.setSeed(seed);
        double r2 = rnd.nextGaussian();

        assertEquals(r1, r2, 0.0);
    }
}
