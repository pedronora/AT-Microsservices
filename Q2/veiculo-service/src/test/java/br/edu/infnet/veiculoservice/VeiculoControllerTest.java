package br.edu.infnet.veiculoservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import br.edu.infnet.veiculoservice.controller.VeiculoController;
import br.edu.infnet.veiculoservice.exceptions.VeiculoNotFoundException;
import br.edu.infnet.veiculoservice.model.Veiculo;
import br.edu.infnet.veiculoservice.payload.MessagePayload;
import br.edu.infnet.veiculoservice.service.VeiculoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(VeiculoController.class)
public class VeiculoControllerTest {
  @Autowired private WebTestClient webClient;

  @MockBean private VeiculoService veiculoService;

  private Veiculo veiculo;

  @Autowired private WebTestClient webTestClient;

  @BeforeEach
  void setUp() {
    veiculo = new Veiculo(1L, "Gol", "Volkswagen", "Branco", "ABC1234", 2020, 123456789);
  }

  @Test
  public void testGetAll() {
    when(veiculoService.getAll()).thenReturn(Flux.just(veiculo));

    webClient
        .get()
        .uri("/veiculos")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Veiculo.class)
        .hasSize(1)
        .contains(veiculo);

    verify(veiculoService, times(1)).getAll();
  }

  @Test
  public void testAddVeiculo() {
    when(veiculoService.add(any(Veiculo.class))).thenReturn(Mono.just(veiculo));

    webClient
        .post()
        .uri("/veiculos")
        .contentType(APPLICATION_JSON)
        .bodyValue(veiculo)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(Veiculo.class)
        .isEqualTo(veiculo);

    verify(veiculoService, times(1)).add(any(Veiculo.class));
  }

  @Test
  public void testGetById_Success() {
    Long id = 1L;
    Veiculo veiculo = new Veiculo(id, "Modelo", "Marca", "Cor", "Placa", 2020, 123456);

    when(veiculoService.getById(id)).thenReturn(Mono.just(veiculo));

    webClient
        .get()
        .uri("/veiculos/{id}", id)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Veiculo.class)
        .consumeWith(
            response -> {
              Veiculo responseVeiculo = response.getResponseBody();
              assertEquals(veiculo.getId(), responseVeiculo.getId());
              assertEquals(veiculo.getModelo(), responseVeiculo.getModelo());
            });

    verify(veiculoService, times(1)).getById(id);
  }

  @Test
  public void testUpdateVeiculo() {
    when(veiculoService.update(eq(1L), any(Veiculo.class))).thenReturn(Mono.just(veiculo));

    webClient
        .put()
        .uri("/veiculos/1")
        .contentType(APPLICATION_JSON)
        .bodyValue(veiculo)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Veiculo.class)
        .isEqualTo(veiculo);

    verify(veiculoService, times(1)).update(eq(1L), any(Veiculo.class));
  }

  @Test
  public void testDeleteVeiculo() {
    when(veiculoService.delete(1L)).thenReturn(Mono.empty());

    webClient.delete().uri("/veiculos/1").exchange().expectStatus().isNoContent();

    verify(veiculoService, times(1)).delete(1L);
  }

  @Test
  public void testAddVeiculoBadRequest() {
    when(veiculoService.add(any(Veiculo.class)))
        .thenThrow(new IllegalArgumentException("Dados inválidos"));

    webClient
        .post()
        .uri("/veiculos")
        .contentType(APPLICATION_JSON)
        .bodyValue(veiculo)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody(MessagePayload.class)
        .consumeWith(
            response -> {
              assertEquals("Dados inválidos", response.getResponseBody().getMessage());
            });

    verify(veiculoService, times(1)).add(any(Veiculo.class));
  }

  @Test
  public void testGetById_NotFound() {
    Long id = 1L;

    when(veiculoService.getById(id))
        .thenThrow(new VeiculoNotFoundException("Veículo com ID " + id + " não encontrado."));

    webClient
        .get()
        .uri("/veiculos/{id}", id)
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectBody(MessagePayload.class)
        .consumeWith(
            response -> {
              assertEquals(
                  "Veículo com ID " + id + " não encontrado.",
                  response.getResponseBody().getMessage());
            });

    verify(veiculoService, times(1)).getById(id);
  }

  @Test
  public void testUpdateVeiculoNotFound() {
    when(veiculoService.update(eq(1L), any(Veiculo.class)))
        .thenThrow(new VeiculoNotFoundException("Veículo não encontrado"));

    webClient
        .put()
        .uri("/veiculos/1")
        .contentType(APPLICATION_JSON)
        .bodyValue(veiculo)
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectBody(MessagePayload.class)
        .consumeWith(
            response -> {
              assertEquals("Veículo não encontrado", response.getResponseBody().getMessage());
            });

    verify(veiculoService, times(1)).update(eq(1L), any(Veiculo.class));
  }

  @Test
  public void testDeleteVeiculoNotFound() {
    doThrow(new VeiculoNotFoundException("Veículo não encontrado")).when(veiculoService).delete(1L);

    webClient
        .delete()
        .uri("/veiculos/1")
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectBody(MessagePayload.class)
        .consumeWith(
            response -> {
              assertEquals("Veículo não encontrado", response.getResponseBody().getMessage());
            });

    verify(veiculoService, times(1)).delete(1L);
  }
}
