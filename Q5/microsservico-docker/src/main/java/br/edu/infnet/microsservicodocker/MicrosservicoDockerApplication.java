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
            title = "Microsserviço de Exemplo: Spring Boot com Kubernetes e Load Balancing",
            version = "1.0",
            description = "Este é um exemplo de microsserviço construído com Spring Boot e implantado em um cluster Kubernetes. O serviço está configurado com balanceamento de carga para distribuir as requisições entre várias réplicas, demonstrando uma arquitetura escalável e resiliente.",
            contact = @Contact(name = "Pedro Nora", email = "pedro.nora@al.infnet.edu.br")))
public class MicrosservicoDockerApplication {

  public static void main(String[] args) {
    SpringApplication.run(MicrosservicoDockerApplication.class, args);
  }
}
