package br.edu.infnet.r2dbcserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("book")
public class Book {

  @Id private Long id;
  private String title;
  private String author;
  private String isbn;
  private String publisher;
  private int publication_year;
}
