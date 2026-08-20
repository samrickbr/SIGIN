package br.com.inova.sigin.pedido.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PedidoPagamentoResponse {

    private Long id;

    private Long formaPagamentoId;

    private String formaPagamento;

    private BigDecimal valor;
}