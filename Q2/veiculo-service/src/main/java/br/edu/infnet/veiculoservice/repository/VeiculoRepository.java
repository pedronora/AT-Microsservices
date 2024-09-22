package br.edu.infnet.veiculoservice.repository;

import br.edu.infnet.veiculoservice.model.Veiculo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface VeiculoRepository extends ReactiveCrudRepository<Veiculo, Long> {}
