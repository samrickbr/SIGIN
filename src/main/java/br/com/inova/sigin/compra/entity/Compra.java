package br.com.inova.sigin.compra.entity;

import br.com.inova.sigin.pessoa.entity.Pessoa;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "compras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Pessoa fornecedor;

    @Column(nullable = false, unique = true)
    private String numero;

    @Column(nullable = false)
    private LocalDateTime dataCompra;

    @Column(nullable = false)
    private String status;

    @Column(length = 500)
    private String observacao;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;
}