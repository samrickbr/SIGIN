package br.com.inova.sigin.pedido.dto;

import br.com.inova.sigin.pedido.enums.SituacaoFinanceira;

import java.math.BigDecimal;

public record PedidoSituacaoFinanceiraResponse(
        Long pedidoId,
        BigDecimal valorTotal,
        BigDecimal valorPago,
        BigDecimal saldoPendente,
        BigDecimal valorExcedente,
        SituacaoFinanceira situacao
) {
}