package br.com.inova.sigin.produtomaterial.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ProdutoMaterialResponse {

    private Long id;

    private Long produtoId;

    private String produto;

    private Long materialId;

    private String material;

    private BigDecimal quantidade;

    private Boolean ativo;

    private LocalDateTime dataCriacao;
}