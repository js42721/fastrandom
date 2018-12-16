package fastrandom;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

public class WELL512aTest {
    @Test
    public void testOutput() throws Exception {
        int[] testState = { 123456789, -59172568, -423908753, -928090592,
                68836822, 1269219612, -77824293, 1060662926, -480941754,
                -1907295641, -1075035823, 292129001, -146556871, 1240875097,
                -120842159, 551407414 };

        WELL512a w = new WELL512a();

        Field field = WELL512a.class.getDeclaredField("s");
        field.setAccessible(true);
        int[] s = (int[]) field.get(w);
        System.arraycopy(testState, 0, s, 0, testState.length);

        for (int i = 0; i < 10; ++i) {
            w.nextInt();
        }

        assertEquals(108509171, w.nextInt());
    }
}
