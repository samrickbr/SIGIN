package br.com.inova.sigin.produtocanal.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProdutoCanalResponse {

    private Long id;

    private Long produtoId;

    private String produto;

    private Long canalVendaId;

    private String canalVenda;

    private BigDecimal precoVenda;

    private Boolean ativo;

}