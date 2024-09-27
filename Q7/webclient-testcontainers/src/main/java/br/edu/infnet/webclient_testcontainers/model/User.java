package br.edu.infnet.webclient_testcontainers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
  private int id;
  private String name;
  private String username;
  private String email;
}
