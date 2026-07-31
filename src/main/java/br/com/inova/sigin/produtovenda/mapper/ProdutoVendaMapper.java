package br.com.inova.sigin.produtovenda.mapper;

import br.com.inova.sigin.canalvenda.entity.CanalVenda;
import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produtovenda.dto.ProdutoVendaRequest;
import br.com.inova.sigin.produtovenda.dto.ProdutoVendaResponse;
import br.com.inova.sigin.produtovenda.entity.ProdutoVenda;

public class ProdutoVendaMapper {

    private ProdutoVendaMapper() {
    }

    public static ProdutoVenda toEntity(
            ProdutoVendaRequest request,
            Produto produto,
            CanalVenda canalVenda) {

        return ProdutoVenda.builder()
                .produto(produto)
                .canalVenda(canalVenda)
                .precoVenda(request.getPrecoVenda())
                .imagem(request.getImagem())
                .disponivelVenda(request.getDisponivelVenda())
                .build();
    }


    public static ProdutoVendaResponse toResponse(
            ProdutoVenda entity) {

        return ProdutoVendaResponse.builder()
                .id(entity.getId())
                .produtoId(entity.getProduto().getId())
                .produto(entity.getProduto().getNome())
                .canalVendaId(entity.getCanalVenda().getId())
                .canalVenda(entity.getCanalVenda().getNome())
                .precoVenda(entity.getPrecoVenda())
                .imagem(entity.getImagem())
                .disponivelVenda(entity.getDisponivelVenda())
                .build();
    }


    public static void updateEntity(
            ProdutoVenda entity,
            ProdutoVendaRequest request,
            Produto produto,
            CanalVenda canalVenda) {

        entity.setProduto(produto);
        entity.setCanalVenda(canalVenda);
        entity.setPrecoVenda(request.getPrecoVenda());
        entity.setImagem(request.getImagem());
        entity.setDisponivelVenda(request.getDisponivelVenda());
    }

}