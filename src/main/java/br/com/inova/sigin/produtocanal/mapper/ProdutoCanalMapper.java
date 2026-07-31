package br.com.inova.sigin.produtocanal.mapper;

import br.com.inova.sigin.canalvenda.entity.CanalVenda;
import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produtocanal.dto.ProdutoCanalRequest;
import br.com.inova.sigin.produtocanal.dto.ProdutoCanalResponse;
import br.com.inova.sigin.produtocanal.entity.ProdutoCanal;

public class ProdutoCanalMapper {

    private ProdutoCanalMapper() {
    }

    public static ProdutoCanal toEntity(
            ProdutoCanalRequest request,
            Produto produto,
            CanalVenda canalVenda) {

        return ProdutoCanal.builder()
                .produto(produto)
                .canalVenda(canalVenda)
                .precoVenda(request.getPrecoVenda())
                .ativo(request.getAtivo())
                .build();
    }

    public static ProdutoCanalResponse toResponse(ProdutoCanal entity) {

        return ProdutoCanalResponse.builder()
                .id(entity.getId())
                .produtoId(entity.getProduto().getId())
                .produto(entity.getProduto().getNome())
                .canalVendaId(entity.getCanalVenda().getId())
                .canalVenda(entity.getCanalVenda().getNome())
                .precoVenda(entity.getPrecoVenda())
                .ativo(entity.getAtivo())
                .build();
    }

    public static void updateEntity(
            ProdutoCanal entity,
            ProdutoCanalRequest request,
            Produto produto,
            CanalVenda canalVenda) {

        entity.setProduto(produto);
        entity.setCanalVenda(canalVenda);
        entity.setPrecoVenda(request.getPrecoVenda());
        entity.setAtivo(request.getAtivo());
    }

}