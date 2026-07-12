package br.com.inova.sigin.movimentacaoestoque.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MovimentacaoEstoqueUpdateRequest {

    @DecimalMin("0.001")
    private BigDecimal quantidade;

    private String observacao;

    private Boolean ativo;
}