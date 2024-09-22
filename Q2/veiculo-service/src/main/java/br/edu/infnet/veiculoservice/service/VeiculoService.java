package br.edu.infnet.veiculoservice.service;

import br.edu.infnet.veiculoservice.exceptions.VeiculoNotFoundException;
import br.edu.infnet.veiculoservice.model.Veiculo;
import br.edu.infnet.veiculoservice.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class VeiculoService {
  private final VeiculoRepository veiculoRepository;

  public Flux<Veiculo> getAll() {
    return veiculoRepository.findAll();
  }

  public Mono<Veiculo> add(Veiculo veiculo) {
    validateVeiculo(veiculo);
    return veiculoRepository.save(veiculo);
  }

  public Mono<Veiculo> getById(Long id) {
    return veiculoRepository
        .findById(id)
        .switchIfEmpty(
            Mono.error(new VeiculoNotFoundException("Veículo com ID " + id + " não encontrado.")));
  }

  public Mono<Veiculo> update(Long id, Veiculo veiculo) {
    return veiculoRepository
        .findById(id)
        .switchIfEmpty(
            Mono.error(new VeiculoNotFoundException("Veículo com ID " + id + " não encontrado.")))
        .flatMap(
            existingVeiculo -> {
              validateVeiculo(veiculo);
              veiculo.setId(id);
              return veiculoRepository.save(veiculo);
            });
  }

  public Mono<Void> delete(Long id) {
    return veiculoRepository
        .existsById(id)
        .flatMap(
            exists -> {
              if (!exists) {
                return Mono.error(
                    new VeiculoNotFoundException("Veículo com ID " + id + " não encontrado."));
              }
              return veiculoRepository.deleteById(id);
            });
  }

  private void validateVeiculo(Veiculo veiculo) {
    if (veiculo.getMarca() == null || veiculo.getMarca().isEmpty()) {
      throw new IllegalArgumentException("A marca do veículo é obrigatória.");
    }
    if (veiculo.getModelo() == null || veiculo.getModelo().isEmpty()) {
      throw new IllegalArgumentException("O modelo do veículo é obrigatório.");
    }
    if (veiculo.getAno() <= 1885) { // Primeiro carro foi criado em 1886
      throw new IllegalArgumentException("O ano do veículo é inválido.");
    }
    if (veiculo.getPlaca() == null || veiculo.getPlaca().isEmpty()) {
      throw new IllegalArgumentException("A placa do veículo é obrigatória.");
    }
    if (String.valueOf(veiculo.getRenavam()).length() != 11) {
      throw new IllegalArgumentException("O RENAVAM deve conter 11 dígitos.");
    }
  }
}
