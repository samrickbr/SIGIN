package br.com.inova.sigin.movimentacaoestoque.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MovimentacaoEstoqueRequest {

    private Long produtoId;

    private Long materialId;

    @NotNull
    private Long localId;

    @NotNull
    private String tipo;

    private String movimento;

    @NotNull
    @DecimalMin("0.001")
    private BigDecimal quantidade;

    @NotNull
    private String origem;

    private Long referenciaId;

    private String observacao;

    private Long responsavelId;
}