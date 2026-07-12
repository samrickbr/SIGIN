package br.com.inova.sigin.ordemproducao.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrdemProducaoUpdateRequest {

    private BigDecimal quantidadePlanejada;

    private BigDecimal quantidadeProduzida;

    private Long localDestinoId;

    private Long responsavelId;

    private String status;

    private String origem;

    private String observacao;

    private Boolean ativo;
}