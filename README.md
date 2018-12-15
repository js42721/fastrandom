FastRandom
==========

A fast, high-quality alternative to java.util.Random. It includes the following
non-cryptographic pseudorandom number generators:

* <b>Mersenne Twister</b> - a 623-dimensionally equidistributed generator with a 
ginormous period of 2<sup>19937</sup> - 1 by Matsumoto and Nishimura.
* <b>taus88</b> - a maximally equidistributed linear feedback shift register by
L'Ecuyer.
* <b>WELL512</b> - part of the WELL (Well Equidistributed Long-period Linear)
series by L'Ecuyer, Matsumoto, and Panneton.

All of these generators perform well on statistical tests of randomness and
should work well for simulations and most other non-cryptographic uses.

The table below provides some basic information about the generators. The
time column shows the time required to generate 10<sup>9</sup> numbers on a
2 GHz Intel Core 2 Duo.

| Name             | Period                | State size (words) | Time (s) | 
| :--------------- | :-------------------- | :----------------- | :------- |
| Mersenne Twister | 2<sup>19937</sup> - 1 | 624                | 3.7      |
| taus88           | ~2<sup>88</sup>       | 3                  | 4.8      |
| WELL512          | 2<sup>512</sup> - 1   | 16                 | 10.7     |

<br>
These generators are implemented in a non-thread-safe manner for the sake of
performance. The recommended way to use them in a multithreaded application is
to have each thread access its own isolated generator instance, thereby avoiding
the need for synchronization or atomic updates.

