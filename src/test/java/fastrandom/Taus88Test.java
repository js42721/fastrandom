package fastrandom;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

public class Taus88Test {
    @Test
    public void testOutput() throws Exception {
        Taus88 t = new Taus88();

        Field f1 = Taus88.class.getDeclaredField("s1");
        f1.setAccessible(true);
        f1.setInt(t, 123456789);

        Field f2 = Taus88.class.getDeclaredField("s2");
        f2.setAccessible(true);
        f2.setInt(t, -59172568);

        Field f3 = Taus88.class.getDeclaredField("s3");
        f3.setAccessible(true);
        f3.setInt(t, -423908753);

        for (int i = 0; i < 10; ++i) {
            t.nextInt();
        }

        assertEquals(1877104589, t.nextInt());
    }
}
