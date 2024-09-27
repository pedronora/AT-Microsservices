package br.edu.infnet.r2dbcserver.repository;

import br.edu.infnet.r2dbcserver.model.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface BookRepository extends ReactiveCrudRepository<Book, Long> {
  Flux<Book> findByAuthor(String author);
}
