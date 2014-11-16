package fastrandom;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

import org.junit.Test;

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
        
        scan.close();
    }
}
