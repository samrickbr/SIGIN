package br.com.inova.sigin.opmaterial.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OpMaterialResponse {

    private Long id;

    private Long ordemProducaoId;

    private String ordemProducao;

    private Long materialId;

    private String material;

    private BigDecimal quantidadePlanejada;

    private BigDecimal quantidadeConsumida;

    private Boolean ativo;

    private LocalDateTime dataCriacao;
}