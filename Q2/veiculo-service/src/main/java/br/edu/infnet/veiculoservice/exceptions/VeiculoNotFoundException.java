package br.edu.infnet.veiculoservice.exceptions;

public class VeiculoNotFoundException extends RuntimeException {
  public VeiculoNotFoundException(String message) {
    super(message);
  }
}
