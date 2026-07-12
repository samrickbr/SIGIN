package br.com.inova.sigin.estoque.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class EstoqueResponse {

    private Long id;

    private String codigo;

    private String nome;

    private String tipo;

    private Long localId;

    private String local;

    private BigDecimal saldo;
}