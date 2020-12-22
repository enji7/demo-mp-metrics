# Demo: MicroProfile Metrics

Basic tech demo for MicroProfile Metrics 2.3.

Usage:

 1. Build and deploy this WAR on a compliant application server.
 1. Run JUnit tests.
 1. Request metrics via browser:
    * <http://localhost:9990/metrics/base>
    * <http://localhost:9990/metrics/vendor>
    * <http://localhost:9990/metrics/application>

## Annotations

 * @Counted
   * increased by 1 for every invocation
 * @SimplyTimed
   * increased by the duration for every invocation
   * also comes with a counter
 * @Gauge
   * simply displays the value that is returned by the annotated method
   * must be applied to @ApplicationScoped beans
 * @ConcurrentGauge
   * measures the number of concurrent requests to the annotated target
   * comes with 3 gauges: current / max / min
 * @Metered
   * comes with
     * total counter
     * rate per second (mean rate since the meter was created)
     * 1m rate per second (one-minute exponentially weighted moving average rate since the meter was created; similiar to Unix "top")
     * 5m rate per second
     * 15m rate per second
 * @Timed
   * comes with
     * "rate per second" gauges (1m / 5m / 15m)
     * min / max / mean / stddev gauges
     * summary (invocation counter and quantiles)

## Resources

 * <https://github.com/eclipse/microprofile-metrics/>
 * <https://github.com/eclipse/microprofile-metrics/releases/>

Resources for the complete MicroProfile spec:

 * <https://projects.eclipse.org/projects/technology.microprofile>
 * <https://github.com/eclipse/microprofile>
 * <https://github.com/eclipse/microprofile/releases>