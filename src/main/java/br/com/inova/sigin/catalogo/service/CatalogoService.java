package br.com.inova.sigin.catalogo.service;

import br.com.inova.sigin.catalogo.dto.CatalogoResponse;
import br.com.inova.sigin.produtovenda.entity.ProdutoVenda;
import br.com.inova.sigin.produtovenda.repository.ProdutoVendaRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogoService {

    private final ProdutoVendaRepository repository;


    public List<CatalogoResponse> listar(Long canalVendaId) {

        List<ProdutoVenda> produtos =
                repository.findCatalogoPorCanal(canalVendaId);

        if (produtos.isEmpty()) {
            throw new RegraNegocioException(
                    "Nenhum produto disponível para este canal."
            );
        }

        return produtos.stream()
                .map(pv -> CatalogoResponse.builder()
                        .canalVendaId(pv.getCanalVenda().getId())
                        .produtoId(pv.getProduto().getId())
                        .produto(pv.getProduto().getNome())
                        .precoVenda(pv.getPrecoVenda())
                        .imagem(pv.getImagem())
                        .build()
                )
                .toList();
    }
}