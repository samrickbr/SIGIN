package br.com.inova.sigin.ordemproducao.mapper;

import br.com.inova.sigin.ordemproducao.dto.OrdemProducaoResponse;
import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import org.springframework.stereotype.Component;

@Component
public class OrdemProducaoMapper {

    public OrdemProducaoResponse toResponse(OrdemProducao op) {

        return OrdemProducaoResponse.builder()
                .id(op.getId())
                .numero(op.getNumero())

                .produtoId(op.getProduto().getId())
                .produto(op.getProduto().getNome())

                .quantidadePlanejada(op.getQuantidadePlanejada())
                .quantidadeProduzida(op.getQuantidadeProduzida())

                .localDestinoId(op.getLocalDestino().getId())
                .localDestino(op.getLocalDestino().getNome())

                .responsavelId(
                        op.getResponsavel() != null
                                ? op.getResponsavel().getId()
                                : null
                )
                .responsavel(
                        op.getResponsavel() != null
                                ? op.getResponsavel().getNome()
                                : null
                )
                .status(op.getStatus().name())
                .origem(op.getOrigem())
                .observacao(op.getObservacao())
                .ativo(op.getAtivo())
                .dataAbertura(op.getDataAbertura())
                .dataInicio(op.getDataInicio())
                .dataFim(op.getDataFim())

                .build();
    }
}