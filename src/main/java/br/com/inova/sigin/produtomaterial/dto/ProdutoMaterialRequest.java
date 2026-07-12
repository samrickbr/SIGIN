package br.com.inova.sigin.produtomaterial.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoMaterialRequest {

    @NotNull
    private Long produtoId;

    @NotNull
    private Long materialId;

    @NotNull
    @DecimalMin(value = "0.001")
    private BigDecimal quantidade;
}