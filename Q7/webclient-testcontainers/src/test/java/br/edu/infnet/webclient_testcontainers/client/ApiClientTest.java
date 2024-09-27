package br.edu.infnet.webclient_testcontainers.client;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import br.edu.infnet.webclient_testcontainers.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
public class ApiClientTest {

  private static final String MOCK_SERVER_IMAGE = "wiremock/wiremock:2.31.0";
  private static GenericContainer<?> mockServer;

  @Autowired private ApiClient apiClient;

  @BeforeEach
  void setUp() {
    if (mockServer == null) {
      mockServer =
          new GenericContainer<>(MOCK_SERVER_IMAGE)
              .withExposedPorts(8080)
              .withCommand("--port", "8080", "--global-response-templating");
      mockServer.start();

      configureFor(mockServer.getHost(), mockServer.getMappedPort(8080));
      stubFor(
          get(urlEqualTo("/users"))
              .willReturn(
                  aResponse()
                      .withHeader("Content-Type", "application/json")
                      .withBody(
                          "[{\"id\": 1, \"name\": \"John Doe\", \"username\": \"johndoe\", \"email\": \"john@example.com\"},"
                              + "{\"id\": 2, \"name\": \"Jane Smith\", \"username\": \"janesmith\", \"email\": \"jane@example.com\"},"
                              + "{\"id\": 3, \"name\": \"Bob Brown\", \"username\": \"bobb\", \"email\": \"bob@example.com\"}]")
                      .withStatus(200)));
    }

    apiClient.setBaseUrl("http://" + mockServer.getHost() + ":" + mockServer.getMappedPort(8080));
  }

  @Test
  void testGetUsers() {
    Flux<User> usersFlux = apiClient.getUsers();

    StepVerifier.create(usersFlux)
        .expectNextMatches(
            user -> {
              return user.getName().equals("Processed: John Doe");
            })
        .expectNextMatches(
            user -> {
              return user.getName().equals("Processed: Jane Smith");
            })
        .expectNextMatches(
            user -> {
              return user.getName().equals("Processed: Bob Brown");
            })
        .verifyComplete();
  }
}
