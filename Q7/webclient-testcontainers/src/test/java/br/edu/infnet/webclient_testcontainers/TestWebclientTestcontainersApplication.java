package br.edu.infnet.webclient_testcontainers;

import org.springframework.boot.SpringApplication;

public class TestWebclientTestcontainersApplication {

	public static void main(String[] args) {
		SpringApplication.from(WebclientTestcontainersApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
