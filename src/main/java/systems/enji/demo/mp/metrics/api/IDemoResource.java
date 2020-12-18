package systems.enji.demo.mp.metrics.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("resource1")
public interface IDemoResource {

  @GET
  @Path("ping")
  String ping();

}
