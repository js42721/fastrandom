package fastrandom;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MersenneTwisterTest {
    @Test
    public void testOutput() throws Exception {
        URL url = getClass().getResource("/mt19937.txt");
        File file = new File(url.getFile());
        Scanner scan = new Scanner(file);

        int[] seed = { 0x123, 0x234, 0x345, 0x456 };
        MersenneTwister mt = new MersenneTwister(seed);

        while (scan.hasNextLong()) {
            long expected = scan.nextLong();
            long actual = mt.nextInt() & 0xffffffffL;
            assertEquals(expected, actual);
        }
        
        while (scan.hasNext()) {
            String expected = scan.next();
            double d = (mt.nextInt() & 0xffffffffL) / 4294967296.0;
            String actual = String.format("%.8f", d);
            assertEquals(expected, actual);
        }

        scan.close();
    }

    @Test
    public void testSeedByArray() {
        int[] seed = { 0, 1, 2 };

        MersenneTwister mt = new MersenneTwister(seed);
        double r1 = mt.nextGaussian();

        mt.setSeed(seed);
        double r2 = mt.nextGaussian();

        assertEquals(r1, r2, 0);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testSeedByArrayEmpty() {
        thrown.expect(IllegalArgumentException.class);
        new MersenneTwister(new int[] {});
    }

    @Test
    public void testSeedByArrayNull() {
        thrown.expect(NullPointerException.class);
        new MersenneTwister(null);
    }
}
