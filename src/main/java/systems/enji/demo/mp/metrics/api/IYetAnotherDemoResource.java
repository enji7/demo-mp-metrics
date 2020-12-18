package systems.enji.demo.mp.metrics.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("resource2")
public interface IYetAnotherDemoResource {

  @GET
  @Path("ping")
  String ping();

}
