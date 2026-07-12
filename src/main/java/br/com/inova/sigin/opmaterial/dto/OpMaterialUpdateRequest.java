package br.com.inova.sigin.opmaterial.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OpMaterialUpdateRequest {

    @DecimalMin("0.001")
    private BigDecimal quantidadePlanejada;

    @DecimalMin("0.000")
    private BigDecimal quantidadeConsumida;

    private Boolean ativo;
}