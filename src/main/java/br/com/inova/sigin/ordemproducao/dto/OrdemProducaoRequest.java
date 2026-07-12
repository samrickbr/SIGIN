package br.com.inova.sigin.ordemproducao.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrdemProducaoRequest {

    @NotBlank
    private String numero;

    @NotNull
    private Long produtoId;

    @NotNull
    @DecimalMin("0.001")
    private BigDecimal quantidadePlanejada;

    @NotNull
    private Long localDestinoId;

    private Long responsavelId;

    @NotBlank
    private String origem;

    private String observacao;
}