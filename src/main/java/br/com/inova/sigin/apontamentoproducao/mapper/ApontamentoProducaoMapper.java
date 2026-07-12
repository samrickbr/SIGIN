package br.com.inova.sigin.apontamentoproducao.mapper;

import br.com.inova.sigin.apontamentoproducao.dto.ApontamentoProducaoResponse;
import br.com.inova.sigin.apontamentoproducao.entity.ApontamentoProducao;
import org.springframework.stereotype.Component;

@Component
public class ApontamentoProducaoMapper {

    public ApontamentoProducaoResponse toResponse(
            ApontamentoProducao entity) {

        return ApontamentoProducaoResponse.builder()

                .id(entity.getId())

                .ordemProducaoId(
                        entity.getOrdemProducao().getId()
                )

                .ordemProducao(
                        entity.getOrdemProducao().getNumero()
                )

                .quantidadeProduzida(
                        entity.getQuantidadeProduzida()
                )

                .quantidadePerda(
                        entity.getQuantidadePerda()
                )

                .responsavelId(
                        entity.getResponsavel() != null
                                ? entity.getResponsavel().getId()
                                : null
                )

                .responsavel(
                        entity.getResponsavel() != null
                                ? entity.getResponsavel().getNome()
                                : null
                )

                .observacao(
                        entity.getObservacao()
                )

                .dataApontamento(
                        entity.getDataApontamento()
                )

                .ativo(
                        entity.getAtivo()
                )

                .build();
    }
}