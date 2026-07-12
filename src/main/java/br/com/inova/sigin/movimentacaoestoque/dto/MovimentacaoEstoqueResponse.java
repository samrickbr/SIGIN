package br.com.inova.sigin.movimentacaoestoque.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class MovimentacaoEstoqueResponse {

    private Long id;

    private Long produtoId;
    private String produto;

    private Long materialId;
    private String material;

    private Long localId;
    private String local;

    private String tipo;

    private String movimento;

    private BigDecimal quantidade;

    private String origem;

    private Long referenciaId;

    private String observacao;

    private Long responsavelId;
    private String responsavel;

    private LocalDateTime dataMovimentacao;

    private Boolean ativo;
}