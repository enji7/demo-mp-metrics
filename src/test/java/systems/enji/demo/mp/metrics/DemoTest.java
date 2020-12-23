package systems.enji.demo.mp.metrics;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

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

  private static WebTarget _targetBase;
  private static WebTarget _targetVendor;
  private static WebTarget _targetApplication;
  
  // beware: WildFly exposes health checks under a different port than the normal application
  private static final String ENDPOINT_METRICS = "http://localhost:9990";
  
  private static final String ENDPOINT_APP = "http://localhost:8080/demo-mp-metrics/";
  
  @BeforeAll
  public static void beforeAll() throws Exception {
    
    // prepare MP REST clients for application invocation
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

    // prepare REST clients for metric retrieval
    _targetBase = ClientBuilder.newClient().target(ENDPOINT_METRICS + "/metrics/base");
    _targetApplication = ClientBuilder.newClient().target(ENDPOINT_METRICS + "/metrics/application");
    _targetVendor = ClientBuilder.newClient().target(ENDPOINT_METRICS + "/metrics/vendor");
    
    // send requests
    _resource1.ping();
    _resource2.ping();
    _resourceGauge.temperature();

  }
  
  /**
   * Base metrics (have to be provided by all compliant servers).
   */
  @Test
  public void base() {
    
    String responseString = _targetBase.request().get(String.class);
    System.out.println(responseString);
   
    assertNotNull(responseString);
    assertTrue(responseString.contains("# TYPE "));
    
  }
  
  /**
   * Vendor-specific metrics.
   */
  @Test
  public void vendor() {
    
    String responseString = _targetVendor.request().get(String.class);
    System.out.println(responseString);

    assertNotNull(responseString);
    assertTrue(responseString.contains("# TYPE "));

  }

  /**
   * Application-specific metrics.
   */
  @Test
  public void application() {
    
    String responseString = _targetApplication.request().get(String.class);
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
    
    // @Metered
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_meter_total"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_meter_rate_per_second"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_meter_one_min_rate_per_second"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_meter_five_min_rate_per_second"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_meter_fifteen_min_rate_per_second"));
    
    // @Timed
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_timer_rate_per_second"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_timer_one_min_rate_per_second"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_timer_five_min_rate_per_second"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_timer_fifteen_min_rate_per_second"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_timer_min_seconds"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_timer_max_seconds"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_timer_mean_seconds"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_timer_stddev_seconds"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_timer_seconds_count"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_timer_seconds{quantile=\"0.5\"}"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_timer_seconds{quantile=\"0.75\"}"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_timer_seconds{quantile=\"0.95\"}"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_timer_seconds{quantile=\"0.98\"}"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_timer_seconds{quantile=\"0.99\"}"));
    assertTrue(responseString.contains("application_systems_enji_demo_mp_metrics_DemoResource_ping_timer_seconds{quantile=\"0.999\"}"));
    
  }

  /**
   * Application-specific metrics in JSON format.
   */
  @Test
  public void applicationJson() {
    
    String responseString = _targetApplication.request().header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON).get(String.class);
    System.out.println(responseString);

    assertNotNull(responseString);
    assertTrue(responseString.contains("systems.enji.demo.mp.metrics.GaugeResource.temperature"));
    
  }

}
