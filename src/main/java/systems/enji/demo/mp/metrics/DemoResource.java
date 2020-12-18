package systems.enji.demo.mp.metrics;

import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

import systems.enji.demo.mp.metrics.api.IDemoResource;

// a counter metric is implicitly included within @SimplyTimed
// @Counted
@SimplyTimed
public class DemoResource implements IDemoResource {

  @Override
  public String ping() {
    return "pong";
  }

}
