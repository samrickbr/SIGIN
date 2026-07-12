package br.com.inova.sigin.apontamentoproducao.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApontamentoProducaoUpdateRequest {

    @DecimalMin("0.000")
    private BigDecimal quantidadeProduzida;


    @DecimalMin("0.000")
    private BigDecimal quantidadePerda;


    private String observacao;


    private Boolean ativo;
}