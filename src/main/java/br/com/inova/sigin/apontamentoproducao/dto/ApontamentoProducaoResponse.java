package br.com.inova.sigin.apontamentoproducao.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ApontamentoProducaoResponse {

    private Long id;

    private Long ordemProducaoId;

    private String ordemProducao;

    private BigDecimal quantidadeProduzida;

    private BigDecimal quantidadePerda;

    private Long responsavelId;

    private String responsavel;

    private String observacao;

    private LocalDateTime dataApontamento;

    private Boolean ativo;
}