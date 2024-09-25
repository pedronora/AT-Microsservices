# Usar a imagem base do Java
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/microsservico-docker-0.0.1-SNAPSHOT.jar /app/microsservico.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "microsservico.jar"]