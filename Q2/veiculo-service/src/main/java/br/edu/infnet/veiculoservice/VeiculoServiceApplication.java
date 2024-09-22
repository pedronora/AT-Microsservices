package br.edu.infnet.veiculoservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
    info =
        @Info(
            title = "Veiculo Service",
            description = "Microsserviço para operações CRUD com a entidade Veiculo"))
@SpringBootApplication
public class VeiculoServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(VeiculoServiceApplication.class, args);
  }
}
