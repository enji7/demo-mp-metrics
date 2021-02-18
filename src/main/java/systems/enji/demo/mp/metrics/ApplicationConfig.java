package systems.enji.demo.mp.metrics;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("")
public class ApplicationConfig extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    // Payara (Jersey) has problems with REST interface classes, so we have to define the implementation class explicitly:
    return Set.of(DemoResource.class, GaugeResource.class, YetAnotherDemoResource.class);
  }

}
