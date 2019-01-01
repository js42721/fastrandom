FastRandom
==========

A faster, more "random" alternative to java.util.Random. It includes the
following non-cryptographic pseudorandom number generators:

* <b>Mersenne Twister</b> - a 623-dimensionally equidistributed generator with a
period of 2<sup>19937</sup> - 1 by Matsumoto and Nishimura.
* <b>Taus88</b> - a maximally equidistributed linear feedback shift register by
L'Ecuyer.
* <b>WELL512a</b> - part of the WELL (Well Equidistributed Long-period Linear)
series by L'Ecuyer, Matsumoto, and Panneton.

All of these generators perform favorably on statistical tests of randomness and
are suitable for simulations and most other non-cryptographic uses.

Use the information in the table below to determine which generator is most
appropriate for your use case. The time column shows the time required to
produce 10<sup>9</sup> numbers on a 2 GHz Intel Core 2 Duo.

| Name             | Period                | State size (words) | Time (s) | 
| :--------------- | :-------------------- | :----------------- | :------- |
| Mersenne Twister | 2<sup>19937</sup> - 1 | 624                | 6.4      |
| Taus88           | ~2<sup>88</sup>       | 3                  | 5.3      |
| WELL512a         | 2<sup>512</sup> - 1   | 16                 | 7.3      |

<br>
Be aware that these generators are implemented in a non-thread-safe manner for
the sake of performance. In a multithreaded application, the recommended course
of action is to have each thread access its own isolated generator instance,
thereby avoiding the need for synchronization or atomic updates.
