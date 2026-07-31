package br.com.inova.sigin.produtovenda.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProdutoVendaResponse {

    private Long id;

    private Long produtoId;

    private String produto;

    private Long canalVendaId;

    private String canalVenda;

    private BigDecimal precoVenda;

    private String imagem;

    private Boolean disponivelVenda;

}