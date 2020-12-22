package systems.enji.demo.mp.metrics.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("gauge-resource")
public interface IGaugeResource {

  @GET
  @Path("temperature")
  int temperature();

}
