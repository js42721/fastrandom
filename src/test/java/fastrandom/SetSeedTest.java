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
        FastRandom rnd = c.newInstance();
        int seed = 123456789;
        rnd.setSeed(seed);
        int r1 = rnd.nextInt();
        rnd.setSeed(seed);
        int r2 = rnd.nextInt();
        assertEquals(r1, r2);
    }
}
