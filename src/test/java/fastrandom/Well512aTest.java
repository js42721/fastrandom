package fastrandom;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.junit.Test;

public class Well512aTest {
    @Test
    public void testOutput() throws Exception {
        int[] expected = { 0x482c89fb, 0x496ffe2c, 0x743dc88e, 0x6a334ca7 };
        
        Well512a w = new Well512a();

        Field field = Well512a.class.getDeclaredField("s");
        field.setAccessible(true);
        int[] s = (int[]) field.get(w);
        Arrays.fill(s, 0xffffffff);

        for (int i = 0; i < 1000000; ++i) {
            w.nextInt();
        }

        for (int n : expected) {
            assertEquals(n, w.nextInt());
        }
    }
}
