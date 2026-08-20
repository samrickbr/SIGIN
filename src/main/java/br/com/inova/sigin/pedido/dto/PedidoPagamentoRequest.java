package br.com.inova.sigin.pedido.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PedidoPagamentoRequest {

    @NotNull
    private Long formaPagamentoId;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal valor;
}