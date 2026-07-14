package br.com.inova.sigin.pedido.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PedidoItemRequest {

    @NotNull
    private Long produtoId;

    @NotNull
    private BigDecimal quantidade;

    private BigDecimal valorUnitario;
}