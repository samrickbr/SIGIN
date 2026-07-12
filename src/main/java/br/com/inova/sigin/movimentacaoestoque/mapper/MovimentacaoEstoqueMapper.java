package br.com.inova.sigin.movimentacaoestoque.mapper;

import br.com.inova.sigin.movimentacaoestoque.dto.MovimentacaoEstoqueResponse;
import br.com.inova.sigin.movimentacaoestoque.entity.MovimentacaoEstoque;
import org.springframework.stereotype.Component;

@Component
public class MovimentacaoEstoqueMapper {

    public MovimentacaoEstoqueResponse toResponse(
            MovimentacaoEstoque entity) {

        return MovimentacaoEstoqueResponse.builder()
                .id(entity.getId())

                .produtoId(
                        entity.getProduto() != null
                                ? entity.getProduto().getId()
                                : null
                )
                .produto(
                        entity.getProduto() != null
                                ? entity.getProduto().getNome()
                                : null
                )

                .materialId(
                        entity.getMaterial() != null
                                ? entity.getMaterial().getId()
                                : null
                )
                .material(
                        entity.getMaterial() != null
                                ? entity.getMaterial().getNome()
                                : null
                )

                .localId(entity.getLocal().getId())
                .local(entity.getLocal().getNome())

                .tipo(entity.getTipo())

                .quantidade(entity.getQuantidade())

                .origem(entity.getOrigem())

                .referenciaId(entity.getReferenciaId())

                .observacao(entity.getObservacao())

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

                .dataMovimentacao(
                        entity.getDataMovimentacao()
                )

                .ativo(entity.getAtivo())

                .build();
    }
}