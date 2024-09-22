package br.edu.infnet.veiculoservice.controller;

import br.edu.infnet.veiculoservice.exceptions.VeiculoNotFoundException;
import br.edu.infnet.veiculoservice.model.Veiculo;
import br.edu.infnet.veiculoservice.payload.MessagePayload;
import br.edu.infnet.veiculoservice.service.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/veiculos")
@Tag(name = "Veículo Controller", description = "Operações CRUD para a entidade Veículo.")
public class VeiculoController {

  private final VeiculoService veiculoService;

  @Operation(
      summary = "Obter todos os veículos",
      description = "Retorna uma lista de todos os veículos cadastrados")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de veículos retornada com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Veiculo.class))),
      })
  @GetMapping
  public ResponseEntity<Flux<Veiculo>> getAll() {
    Flux<Veiculo> veiculos = veiculoService.getAll();
    veiculos.count().subscribe(count -> log.info("Lista de " + count + " veículos"));
    return ResponseEntity.ok(veiculos);
  }

  @Operation(
      summary = "Adicionar um novo veículo",
      description = "Adiciona um novo veículo ao catálogo")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Veículo adicionado com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Veiculo.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Erro na validação dos dados do veículo",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MessagePayload.class)))
      })
  @PostMapping
  public ResponseEntity<?> add(@RequestBody Veiculo veiculo) {
    try {
      Mono<Veiculo> createdVeiculo = veiculoService.add(veiculo);
      log.info("Novo veículo criado com sucesso!");
      return ResponseEntity.status(HttpStatus.CREATED).body(createdVeiculo);
    } catch (IllegalArgumentException e) {
      log.error("Erro ao adicionar veículo: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessagePayload(e.getMessage()));
    }
  }

  @Operation(
      summary = "Obter um veículo pelo ID",
      description = "Retorna os detalhes de um veículo específico com base no ID fornecido")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Veículo encontrado com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Veiculo.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Veículo não encontrado",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MessagePayload.class)))
      })
  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Long id) {
    try {
      Mono<Veiculo> veiculo = veiculoService.getById(id);
      return ResponseEntity.ok(veiculo);
    } catch (VeiculoNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
    }
  }

  @Operation(
      summary = "Atualizar um veículo",
      description = "Atualiza os dados de um veículo existente")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Veículo atualizado com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Veiculo.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Veículo não encontrado",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MessagePayload.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Erro na validação dos dados do veículo",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MessagePayload.class)))
      })
  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Veiculo veiculo) {
    try {
      Mono<Veiculo> updatedVeiculo = veiculoService.update(id, veiculo);
      return ResponseEntity.ok(updatedVeiculo);
    } catch (VeiculoNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessagePayload(e.getMessage()));
    }
  }

  @Operation(summary = "Excluir um veículo", description = "Remove um veículo do catálogo")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Veículo removido com sucesso"),
        @ApiResponse(
            responseCode = "404",
            description = "Veículo não encontrado",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MessagePayload.class)))
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    try {
      veiculoService.delete(id);
      return ResponseEntity.noContent().build();
    } catch (VeiculoNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
    }
  }
}
