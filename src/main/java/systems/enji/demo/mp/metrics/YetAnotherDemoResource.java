package systems.enji.demo.mp.metrics;

import org.eclipse.microprofile.metrics.annotation.SimplyTimed;

import systems.enji.demo.mp.metrics.api.IYetAnotherDemoResource;

@SimplyTimed
public class YetAnotherDemoResource implements IYetAnotherDemoResource {

  @Override
  public String ping() {
    return "pong";
  }

}
