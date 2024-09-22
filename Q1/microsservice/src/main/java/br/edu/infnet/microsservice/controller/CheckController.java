package br.edu.infnet.microsservice.controller;

import br.edu.infnet.microsservice.payload.MessagePayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Slf4j
@Tag(name = "Check Controller", description = "Endpoint para verificar o status do serviço")
public class CheckController {

  @Operation(
      summary = "Verificar o status do sistema",
      description = "Retorna o status do sistema se ele estiver funcionando corretamente.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Sistema está ativo",
            content = {@Content(schema = @Schema(implementation = MessagePayload.class))}),
        @ApiResponse(
            responseCode = "500",
            description = "Ocorreu um erro interno no servidor",
            content = {@Content(schema = @Schema(implementation = MessagePayload.class))}),
      })
  @GetMapping
  public ResponseEntity<MessagePayload> checkStatus() {
    try {
      log.info("Status: Ativo");
      return ResponseEntity.status(HttpStatus.OK).body(new MessagePayload("Ativo"));
    } catch (Exception e) {
      log.error("Status: Error");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new MessagePayload(e.getMessage()));
    }
  }
}
