package br.com.inova.sigin.configuracao.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ConfiguracaoSistemaRequest {

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal taxaEntregaPadrao;
}