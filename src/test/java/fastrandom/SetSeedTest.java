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
                { MersenneTwister.class },
                { Taus88.class },
                { Well512a.class }
        });
    }

    private Class<? extends FastRandom> c;

    public SetSeedTest(Class<? extends FastRandom> c) {
        this.c = c;
    }

    @Test
    public void testSetSeed() throws Exception {
        int n = 1000000;
        double[] expected = new double[5];

        long seed = Utils.getSeed();
        FastRandom rnd = c.getConstructor(Long.TYPE).newInstance(seed);
        
        for (int i = 0; i < n; ++i) {
            rnd.nextInt();
        }

        for (int i = 0; i < expected.length; ++i) {
            expected[i] = rnd.nextGaussian();
        }

        rnd.setSeed(seed);

        for (int i = 0; i < n; ++i) {
            rnd.nextInt();
        }

        for (double e : expected) {
            assertEquals(e, rnd.nextGaussian(), 0);
        }
    }
}
