package br.edu.infnet.microsservicodocker.service;

import br.edu.infnet.microsservicodocker.model.Livro;
import br.edu.infnet.microsservicodocker.repository.LivroRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LivroService {

  private final LivroRepository livroRepository;

  public List<Livro> getAll() {
    return livroRepository.findAll();
  }

  public Optional<Livro> getById(Long id) {
    return livroRepository.findById(id);
  }

  public Livro save(Livro livro) {
    return livroRepository.save(livro);
  }

  public void delete(Long id) {
    livroRepository.deleteById(id);
  }
}
