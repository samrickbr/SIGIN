package br.com.inova.sigin.api.delivery.carrinho.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class CarrinhoResponse {

    private Long id;

    private String status;

    private BigDecimal valorTotal;

    private List<CarrinhoItemResponse> itens;

}