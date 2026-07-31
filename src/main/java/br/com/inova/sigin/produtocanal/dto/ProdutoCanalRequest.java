package br.com.inova.sigin.produtocanal.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoCanalRequest {

    @NotNull
    private Long produtoId;

    @NotNull
    private Long canalVendaId;

    @DecimalMin(value = "0.00")
    private BigDecimal precoVenda;

    private Boolean ativo = true;

}