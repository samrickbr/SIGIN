package br.com.inova.sigin.material.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MaterialUpdateRequest {

    private String nome;

    private String descricao;

    private String unidadeMedida;

    private BigDecimal estoqueMinimo;

    private Boolean ativo;
}