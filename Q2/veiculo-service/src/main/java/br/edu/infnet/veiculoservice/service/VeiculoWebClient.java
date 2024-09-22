package br.edu.infnet.veiculoservice.service;

import br.edu.infnet.veiculoservice.model.Veiculo;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VeiculoWebClient {
  private final WebClient webClient;

  public VeiculoWebClient(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("http://localhost:8080/veiculos").build();
  }

  public Flux<Veiculo> getAll() {
    return webClient.get().retrieve().bodyToFlux(Veiculo.class);
  }

  public Mono<Veiculo> getById(Long id) {
    return webClient.get().uri("/{id}", id).retrieve().bodyToMono(Veiculo.class);
  }

  public Mono<Veiculo> add(Veiculo veiculo) {
    return webClient.post().bodyValue(veiculo).retrieve().bodyToMono(Veiculo.class);
  }

  public Mono<Veiculo> update(Long id, Veiculo veiculo) {
    return webClient.put().uri("/{id}", id).bodyValue(veiculo).retrieve().bodyToMono(Veiculo.class);
  }

  public Mono<Void> delete(Long id) {
    return webClient.delete().uri("/{id}", id).retrieve().bodyToMono(Void.class);
  }
}
