package br.edu.infnet.webclient_testcontainers.client;

import br.edu.infnet.webclient_testcontainers.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class ApiClient {

  private WebClient webClient;

  public ApiClient(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.build();
  }

  public void setBaseUrl(String baseUrl) {
    this.webClient = this.webClient.mutate().baseUrl(baseUrl).build();
  }

  public Flux<User> getUsers() {
    return webClient
        .get()
        .uri("/users")
        .retrieve()
        .bodyToFlux(User.class)
        .map(
            user ->
                new User(
                    user.getId(),
                    "Processed: " + user.getName(),
                    user.getUsername(),
                    user.getEmail()));
  }
}
