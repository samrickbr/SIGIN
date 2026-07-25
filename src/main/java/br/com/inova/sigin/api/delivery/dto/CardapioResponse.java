package br.com.inova.sigin.api.delivery.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CardapioResponse {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String imagem;
    private String categoria;

}