package br.com.inova.sigin.pedido.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PedidoItemRequest {

    @NotNull
    private Long produtoId;

    @NotNull
    @Positive
    private BigDecimal quantidade;
}