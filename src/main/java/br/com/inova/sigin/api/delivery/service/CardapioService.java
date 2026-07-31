package br.com.inova.sigin.api.delivery.service;

import br.com.inova.sigin.api.delivery.dto.CardapioResponse;
import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produtovenda.service.ProdutoVendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardapioService {

    private final ProdutoVendaService produtoVendaService;
    public List<CardapioResponse> listar() {

        return produtoVendaService
                .listarDisponiveisEntity()
                .stream()
                .map(produtoVenda -> {

                    Produto produto = produtoVenda.getProduto();

                    return CardapioResponse.builder()
                            .id(produto.getId())
                            .nome(produto.getNome())
                            .descricao(produto.getDescricao())
                            .preco(produtoVenda.getPrecoVenda())
                            .imagem(produtoVenda.getImagem())
                            .categoria(
                                    produto.getCategoria() != null
                                            ? produto.getCategoria().getNome()
                                            : null
                            )
                            .build();
                })
                .toList();
    }
}