package br.com.inova.sigin.opmaterial.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OpMaterialRequest {

    @NotNull
    private Long ordemProducaoId;

    @NotNull
    private Long materialId;

    @NotNull
    @DecimalMin("0.001")
    private BigDecimal quantidadePlanejada;
}