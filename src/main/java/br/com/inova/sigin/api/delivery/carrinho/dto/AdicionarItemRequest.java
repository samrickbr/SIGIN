package br.com.inova.sigin.api.delivery.carrinho.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdicionarItemRequest {

    @NotNull
    private Long produtoVendaId;

    @NotNull
    private BigDecimal quantidade;

}