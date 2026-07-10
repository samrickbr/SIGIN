package br.com.inova.sigin.pessoa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tipos_pessoa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoPessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
}