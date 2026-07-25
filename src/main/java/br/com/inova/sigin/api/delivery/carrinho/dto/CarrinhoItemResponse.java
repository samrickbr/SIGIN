package br.com.inova.sigin.api.delivery.carrinho.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CarrinhoItemResponse {

    private Long id;

    private Long produtoId;

    private String produto;

    private BigDecimal quantidade;

    private BigDecimal valorUnitario;

    private BigDecimal valorTotal;

}