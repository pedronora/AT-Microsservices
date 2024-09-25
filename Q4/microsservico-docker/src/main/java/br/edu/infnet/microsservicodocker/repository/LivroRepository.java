package br.edu.infnet.microsservicodocker.repository;

import br.edu.infnet.microsservicodocker.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {}
