package br.com.inova.sigin.configuracao.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ConfiguracaoSistemaResponse {

    private BigDecimal taxaEntregaPadrao;
}