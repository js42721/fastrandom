package fastrandom;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

public class SuperDuper64Test {
    @Test
    public void testOutput() throws Exception {
        long[] expected = { 5493644966311506127L, 7769516696436506811L,
                -96814496343826443L, -8093909087313116643L, 7532658832815845960L };
        
        SuperDuper64 sd = new SuperDuper64();
        
        Field f1 = SuperDuper64.class.getDeclaredField("x");
        f1.setAccessible(true);
        f1.setLong(sd, 123456789);
        
        Field f2 = SuperDuper64.class.getDeclaredField("y");
        f2.setAccessible(true);
        f2.setLong(sd, 987654321);
        
        for (int i = 0; i < 100000000; ++i) {
            sd.nextLong();
        }
        
        for (int i = 0; i < 5; ++i) {
            assertEquals(expected[i], sd.nextLong());
        }
    }
}
