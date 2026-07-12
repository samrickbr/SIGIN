package br.com.inova.sigin.produtomaterial.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoMaterialUpdateRequest {

    @DecimalMin(value = "0.001")
    private BigDecimal quantidade;

    private Boolean ativo;
}