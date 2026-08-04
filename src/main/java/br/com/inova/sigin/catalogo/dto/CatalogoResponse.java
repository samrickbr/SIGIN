package br.com.inova.sigin.catalogo.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CatalogoResponse {

    private Long canalVendaId;

    private Long produtoId;

    private String produto;

    private BigDecimal precoVenda;

    private String imagem;

}