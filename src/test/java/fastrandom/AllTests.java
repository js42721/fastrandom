package fastrandom;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ LFib4Test.class, MersenneTwisterTest.class,
    SuperDuper64Test.class, Taus88Test.class, WELL512Test.class,
    SetSeedTest.class })
public class AllTests {
}
