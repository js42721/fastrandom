FastRandom
==========

A fast, high-quality alternative to java.util.Random. It includes the following
non-cryptographic pseudorandom number generators:

* <b>LFib4</b> - a lagged Fibonacci generator by Marsaglia.
* <b>MersenneTwister</b> - a 623-dimensionally equidistributed generator with a 
ginormous period of 2<sup>19937</sup> - 1 by Matsumoto and Nishimura.
* <b>SuperDuper64</b> - a combination of a linear congruential generator and a
linear feedback shift register by Marsaglia.
* <b>Taus88</b> - a maximally equidistributed linear feedback shift register by
L'Ecuyer.
* <b>WELL512</b> - the smallest of the WELL (Well Equidistributed Long-period
Linear) series by L'Ecuyer, Matsumoto, and Panneton.

All of these generators perform well on statistical tests of randomness. See 
["TestU01: A C Library for Empirical Testing of Random Number Generators"](
http://www.iro.umontreal.ca/~lecuyer/myftp/papers/testu01.pdf)
by L'Ecuyer and Simard to view test results for some of them.

The following table provides some basic information about the generators.

| Name            | Period                | Size\* (bits) | Speed\*\* (relative to fastest) | 
| :-------------- | :-------------------- | :------------ | :------------------------------ |
| LFib4           | ~2<sup>287</sup>      | 8192          | 100%                            |
| MersenneTwister | 2<sup>19937</sup> - 1 | 19968         | 38%                             |
| SuperDuper64    | ~2<sup>128</sup>      | 128           | 51%                             |
| Taus88          | ~2<sup>88</sup>       | 96            | 75%                             |
| WELL512         | 2<sup>512</sup> - 1   | 512           | 51%                             |

\* Java object overhead and auxiliary variables are not taken into consideration.

\*\* Measured by generating 32-bit integers.


These generators are implemented in a non-thread-safe manner for the sake of
performance. The recommended way to use them in a multithreaded application is
to have each thread access its own isolated generator instance, thereby avoiding
the need for synchronization or atomic updates (the stuff that makes Random
slow). Java 7's ThreadLocalRandom follows this route and is very fast as a
result, though it is somewhat lacking as far as randomness is concerned.

