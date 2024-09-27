package br.edu.infnet.r2dbcserver.controller;

import br.edu.infnet.r2dbcserver.model.Book;
import br.edu.infnet.r2dbcserver.repository.BookRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

  private final BookRepository bookRepository;

  @GetMapping()
  public Mono<ResponseEntity<Flux<Book>>> getAllBooks() {
    Flux<Book> books = bookRepository.findAll();
    return books
        .hasElements()
        .flatMap(
            hasBooks -> {
              if (hasBooks) {
                return Mono.just(ResponseEntity.ok(books));
              } else {
                return Mono.just(ResponseEntity.notFound().build());
              }
            });
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Book>> getBookById(@PathVariable Long id) {
    return bookRepository
        .findById(id)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<Book>> createBook(@RequestBody Book book) {
    return bookRepository
        .save(book)
        .map(savedBook -> ResponseEntity.status(HttpStatus.CREATED).body(savedBook));
  }

  @GetMapping("/author/{author}")
  public Flux<ResponseEntity<Book>> getBooksByAuthor(@PathVariable String author) {
    return bookRepository
        .findByAuthor(author)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<Book>> updateBook(@PathVariable Long id, @RequestBody Book book) {

    return bookRepository
        .findById(id)
        .flatMap(
            existingBook -> {
              existingBook.setTitle(book.getTitle());
              existingBook.setAuthor(book.getAuthor());
              return bookRepository.save(existingBook);
            })
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> deleteBook(@PathVariable Long id) {
    return bookRepository
        .findById(id)
        .flatMap(bookRepository::delete)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}
