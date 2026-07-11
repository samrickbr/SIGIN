package br.com.inova.sigin.material.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class MaterialResponse {

    private Long id;

    private String codigo;

    private String nome;

    private String descricao;

    private String unidadeMedida;

    private BigDecimal estoqueMinimo;

    private Boolean ativo;

    private LocalDateTime dataCriacao;
}