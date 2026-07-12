package br.com.inova.sigin.ordemproducao.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OrdemProducaoResponse {

    private Long id;

    private String numero;

    private Long produtoId;
    private String produto;

    private BigDecimal quantidadePlanejada;
    private BigDecimal quantidadeProduzida;

    private Long localDestinoId;
    private String localDestino;

    private Long responsavelId;
    private String responsavel;

    private String status;
    private String origem;

    private String observacao;

    private Boolean ativo;

    private LocalDateTime dataAbertura;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
}