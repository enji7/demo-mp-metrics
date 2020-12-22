package systems.enji.demo.mp.metrics;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import systems.enji.demo.mp.metrics.api.IDemoResource;
import systems.enji.demo.mp.metrics.api.IGaugeResource;
import systems.enji.demo.mp.metrics.api.IYetAnotherDemoResource;

/**
 * This test requires the demo service to be up and running.
 */
public class DemoTest {

  private static IDemoResource _resource1;
  private static IYetAnotherDemoResource _resource2;
  private static IGaugeResource _resourceGauge;

  
  // beware: WildFly exposes health checks under a different port than the normal application
  private static final String ENDPOINT = "http://localhost:9990";
  
  private static final String ENDPOINT_APP = "http://localhost:8080/demo-mp-metrics/";
  
  @BeforeAll
  public static void beforeAll() throws Exception {
    _resource1 = RestClientBuilder.newBuilder()
        .baseUri(URI.create(ENDPOINT_APP))
        .register(JacksonJsonProvider.class)
        .build(IDemoResource.class);
    _resource2 = RestClientBuilder.newBuilder()
        .baseUri(URI.create(ENDPOINT_APP))
        .register(JacksonJsonProvider.class)
        .build(IYetAnotherDemoResource.class);
    _resourceGauge = RestClientBuilder.newBuilder()
        .baseUri(URI.create(ENDPOINT_APP))
        .register(JacksonJsonProvider.class)
        .build(IGaugeResource.class);
  }
  
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
    
    _resource1.ping();
    _resource2.ping();
    _resourceGauge.temperature();
    
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target(ENDPOINT + "/metrics/application");
    
    String responseString = target.request().get(String.class);
    System.out.println(responseString);

    assertNotNull(responseString);
    assertTrue(responseString.contains("# TYPE "));
    
    // @Counted / @SimplyTimed
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_DemoResource_total"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_total"));

    // @SimplyTimed
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_DemoResource_elapsedTime_seconds"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_elapsedTime_seconds"));
    
    // @Gauge
    // this metric only appears after the first explicit invocation of the annotated method
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_GaugeResource_temperature_degrees"));
    
    // @ConcurrentGauge
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_concurrent_gauge_current"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_concurrent_gauge_max"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_concurrent_gauge_min"));
    
  }

}
