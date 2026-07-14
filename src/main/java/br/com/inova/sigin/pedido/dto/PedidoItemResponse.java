package br.com.inova.sigin.pedido.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PedidoItemResponse {

    private Long id;

    private Long produtoId;

    private String produto;

    private BigDecimal quantidade;

    private BigDecimal valorUnitario;

    private BigDecimal valorTotal;
}