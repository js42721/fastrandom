package fastrandom;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ MersenneTwisterTest.class,
                Taus88Test.class,
                WELL512aTest.class,
                SetSeedTest.class })
public class AllTests {
}
