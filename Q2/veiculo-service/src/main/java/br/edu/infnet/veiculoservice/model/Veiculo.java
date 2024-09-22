package br.edu.infnet.veiculoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "veiculos")
public class Veiculo {
  @Id
  private Long id;

  private String modelo;
  private String marca;
  private String cor;
  private String placa;
  private int ano;
  private int renavam;
}
