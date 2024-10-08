package br.edu.infnet.microsservicodocker;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info =
        @Info(
            title = "Microsserviço Docker API",
            version = "1.0",
            description = "API do microsserviço rodando em Docker",
            contact = @Contact(name = "Pedro Nora", email = "pedro.nora@al.infnet.edu.br")))
public class MicrosservicoDockerApplication {

  public static void main(String[] args) {
    SpringApplication.run(MicrosservicoDockerApplication.class, args);
  }
}
