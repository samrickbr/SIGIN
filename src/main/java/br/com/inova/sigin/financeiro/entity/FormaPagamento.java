package br.com.inova.sigin.financeiro.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "formas_pagamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String descricao;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(nullable = false)
    private Boolean baixaAutomatica;
}