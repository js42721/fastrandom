package fastrandom;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

public class KISSTest {
    @Test
    public void testOutput() throws Exception {
        int[] expected = { 199275006, 86473693, -2085369775, 1298124039 };

        KISS kiss = new KISS();

        Field f1 = KISS.class.getDeclaredField("x");
        f1.setAccessible(true);
        f1.setInt(kiss, 123456789);

        Field f2 = KISS.class.getDeclaredField("y");
        f2.setAccessible(true);
        f2.setInt(kiss, 362436069);

        Field f3 = KISS.class.getDeclaredField("z");
        f3.setAccessible(true);
        f3.setInt(kiss, 21288629);

        Field f4 = KISS.class.getDeclaredField("w");
        f4.setAccessible(true);
        f4.setInt(kiss, 14921776);

        Field f5 = KISS.class.getDeclaredField("c");
        f5.setAccessible(true);
        f5.setInt(kiss, 0);

        for (int i = 0; i < 99996; ++i) {
            kiss.nextInt();
        }

        for (int i = 0; i < 4; ++i) {
            assertEquals(expected[i], kiss.nextInt());
        }
    }
}
