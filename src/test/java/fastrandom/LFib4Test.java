package fastrandom;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

public class LFib4Test {
    @Test
    public void testOutput() throws Exception {
        LFib4 fib = new LFib4();
        
        Field field = LFib4.class.getDeclaredField("state");
        field.setAccessible(true);
        int[] state = (int[])field.get(fib);
        
        int z     = 12345;
        int w     = 65435;
        int jsr   = 12345;
        int jcong = 34221;
       
        for (int i = 0; i < 256; ++i) {
            jsr = 69069 * jsr + 1234567;
            jcong ^= jcong << 17;
            jcong ^= jcong >>> 13;
            jcong ^= jcong << 5;
            z = 36969 * (z & 65535) + (z >>> 16);
            w = 18000 * (w & 65535) + (w >>> 16);
            state[i] = (((z << 16) + w) ^ jsr) + jcong;
        }
        
        int k = 0;
        for (int i = 1; i < 1000001; ++i) {
            k = fib.nextInt();
        }
        
        assertEquals(0, k - 1064612766);
    }
}
