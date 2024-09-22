package br.edu.infnet.microsservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Assessment", description = "Microsserviço"))
@SpringBootApplication
public class MicrosserviceApplication {

  public static void main(String[] args) {
    SpringApplication.run(MicrosserviceApplication.class, args);
  }
}
