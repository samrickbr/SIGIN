package br.com.inova.sigin.apontamentoproducao.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApontamentoProducaoRequest {

    @NotNull
    private Long ordemProducaoId;


    @NotNull
    @DecimalMin("0.000")
    private BigDecimal quantidadeProduzida;


    @NotNull
    @DecimalMin("0.000")
    private BigDecimal quantidadePerda;


    private Long responsavelId;


    private String observacao;
}