package br.edu.infnet.microsservicodocker.controller;

import br.edu.infnet.microsservicodocker.model.Livro;
import br.edu.infnet.microsservicodocker.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/livros")
@Tag(name = "Livros", description = "API para gerenciamento de livros")
public class LivroController {

  private final LivroService livroService;

  @Operation(
      summary = "Buscar todos os livros",
      description = "Retorna uma lista de todos os livros disponíveis")
  @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso")
  @GetMapping
  public List<Livro> getAll() {
    return livroService.getAll();
  }

  @Operation(
      summary = "Buscar livro por ID",
      description = "Retorna um livro com base no ID fornecido")
  @ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso")
  @ApiResponse(responseCode = "404", description = "Livro não encontrado")
  @GetMapping("/{id}")
  public ResponseEntity<Livro> getById(@PathVariable Long id) {
    Optional<Livro> livro = livroService.getById(id);
    return livro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(summary = "Criar novo livro", description = "Cria um novo livro e o retorna")
  @ApiResponse(responseCode = "201", description = "Livro criado com sucesso")
  @PostMapping
  public Livro create(@RequestBody Livro livro) {
    return livroService.save(livro);
  }

  @Operation(
      summary = "Atualizar livro por ID",
      description = "Atualiza um livro com base no ID fornecido")
  @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso")
  @ApiResponse(responseCode = "404", description = "Livro não encontrado")
  @PutMapping("/{id}")
  public ResponseEntity<Livro> update(@PathVariable Long id, @RequestBody Livro livro) {
    Optional<Livro> updatedLivro = livroService.update(id, livro);
    return updatedLivro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Operation(
      summary = "Excluir livro por ID",
      description = "Exclui um livro com base no ID fornecido")
  @ApiResponse(responseCode = "200", description = "Livro excluído com sucesso")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    livroService.delete(id);
    return ResponseEntity.ok().build();
  }
}
