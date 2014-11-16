package fastrandom;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SetSeedTest {
    @Parameters
    public static Iterable<Object[]> getAll() {
        return Arrays.asList(new Object[][] { 
                { LFib4.class },
                { MersenneTwister.class },
                { SuperDuper64.class },
                { Taus88.class },
                { WELL512.class }
        });
    }
    
    private Class<? extends FastRandom> c;
    private FastRandom rnd;
    
    public SetSeedTest(Class<? extends FastRandom> c) {
        this.c = c;
    }

    @Before
    public void init() throws Exception {
        rnd = c.newInstance();
    }
    
    @Test
    public void testSetSeed() {
        int seed = 123456789;
        rnd.setSeed(seed);
        int r1 = rnd.nextInt();
        rnd.setSeed(seed);
        int r2 = rnd.nextInt();
        assertEquals(r1, r2);
    }
}
