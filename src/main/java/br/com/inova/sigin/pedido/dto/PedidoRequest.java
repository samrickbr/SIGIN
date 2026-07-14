package br.com.inova.sigin.pedido.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PedidoRequest {

    @NotBlank
    private String numero;

    @NotNull
    private Long clienteId;

    private BigDecimal valorTotal;

    private String observacao;
}