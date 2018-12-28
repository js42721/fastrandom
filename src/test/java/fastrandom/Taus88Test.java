package fastrandom;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

public class Taus88Test {
    @Test
    public void testOutput() throws Exception {
        int[] expected = { 0x78b3e036, 0x2c777c2e, 0x99dc791c, 0x24a8efe0 };

        Taus88 t = new Taus88();

        Field f0 = Taus88.class.getDeclaredField("s0");
        f0.setAccessible(true);
        f0.setInt(t, 0xffffffff);

        Field f1 = Taus88.class.getDeclaredField("s1");
        f1.setAccessible(true);
        f1.setInt(t, 0xffffffff);

        Field f2 = Taus88.class.getDeclaredField("s2");
        f2.setAccessible(true);
        f2.setInt(t, 0xffffffff);

        for (int i = 0; i < 1000000; ++i) {
            t.nextInt();
        }

        for (int n : expected) {
            assertEquals(n, t.nextInt());
        }
    }
}
