package br.com.inova.sigin.material.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MaterialRequest {

    @NotBlank
    private String codigo;

    @NotBlank
    private String nome;

    private String descricao;

    @NotBlank
    private String unidadeMedida;

    @NotNull
    private BigDecimal estoqueMinimo;
}