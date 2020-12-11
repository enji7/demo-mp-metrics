package systems.enji.demo.mp.metrics;

import java.net.URI;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeAll;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import systems.enji.demo.mp.metrics.api.IDemoService;

/**
 * This test requires the demo service to be up and running.
 */
public class DemoTest {

  private static IDemoService _client;

  private static final String ENDPOINT = "http://localhost:8080/demo-mp-metrics/";

  @BeforeAll
  static void beforeAll() {
    RestClientBuilder builder = RestClientBuilder.newBuilder().baseUri(URI.create(ENDPOINT)).register(JacksonJsonProvider.class);
    _client = builder.build(IDemoService.class);
  }

}
