package br.edu.infnet.webclient_testcontainers.controller;

import br.edu.infnet.webclient_testcontainers.client.ApiClient;
import br.edu.infnet.webclient_testcontainers.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequestMapping("/api/users")
@RestController
public class UserController {

  private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
  private final ApiClient apiClient;

  @Autowired
  public UserController(ApiClient apiClient) {
    this.apiClient = apiClient;
    this.apiClient.setBaseUrl(BASE_URL);
  }

  @GetMapping
  public Flux<User> getUsers() {
    return apiClient.getUsers();
  }
}
