package systems.enji.demo.mp.metrics;

import java.util.Random;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.metrics.annotation.Gauge;

import systems.enji.demo.mp.metrics.api.IGaugeResource;

// Gauges are special in that they only work with @ApplicationScoped beans
@ApplicationScoped
public class GaugeResource implements IGaugeResource {

  @Gauge(unit = "degrees")
  @Override
  public int temperature() {
    return 20 + new Random().nextInt(10);
  }

}
