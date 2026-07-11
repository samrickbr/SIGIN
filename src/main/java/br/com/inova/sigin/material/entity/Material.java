package br.com.inova.sigin.material.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "materiais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String codigo;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @Column(name = "unidade_medida", nullable = false, length = 20)
    private String unidadeMedida;

    @Column(name = "estoque_minimo", precision = 12, scale = 3)
    private BigDecimal estoqueMinimo;

    @Column(nullable = false)
    @Builder.Default
    private Boolean ativo = true;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
}