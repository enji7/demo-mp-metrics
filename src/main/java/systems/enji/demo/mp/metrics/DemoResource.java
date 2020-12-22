package systems.enji.demo.mp.metrics;

import org.eclipse.microprofile.metrics.annotation.ConcurrentGauge;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

import systems.enji.demo.mp.metrics.api.IDemoResource;

// a counter metric is implicitly included within @SimplyTimed
// @Counted
@SimplyTimed
public class DemoResource implements IDemoResource {

  // the name has to be provided explicitly to prevent collision with @SimplyTimed
  @ConcurrentGauge(name = "ping_concurrent_gauge")
  @Metered(name = "ping_meter")
  @Override
  public String ping() {
    return "pong";
  }

}
