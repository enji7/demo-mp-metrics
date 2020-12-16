package systems.enji.demo.mp.metrics;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.jupiter.api.Test;

/**
 * This test requires the demo service to be up and running.
 */
public class DemoTest {

  // beware: WildFly exposes health checks under a different port than the normal application
  private static final String ENDPOINT = "http://localhost:9990";

  /**
   * Base metrics (have to be provided by all compliant servers).
   */
  @Test
  public void base() {
    
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target(ENDPOINT + "/metrics/base");
    
    String responseString = target.request().get(String.class);
    System.out.println(responseString);
   
    assertNotNull(responseString);
    assertTrue(responseString.contains("# TYPE "));
    
  }
  
  /**
   * Vendor-specific metrics.
   */
  @Test
  public void vendor() {
    
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target(ENDPOINT + "/metrics/vendor");
    
    String responseString = target.request().get(String.class);
    System.out.println(responseString);

    assertNotNull(responseString);
    assertTrue(responseString.contains("# TYPE "));

  }

  /**
   * Application-specific metrics.
   */
  @Test
  public void application() {
    
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target(ENDPOINT + "/metrics/application");
    
    String responseString = target.request().get(String.class);
    System.out.println(responseString);

    assertNotNull(responseString);
    assertTrue(responseString.contains("# TYPE "));

  }

}
