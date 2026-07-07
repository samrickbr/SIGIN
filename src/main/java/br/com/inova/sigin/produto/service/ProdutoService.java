package br.com.inova.sigin.produto.service;

import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produto.repository.ProdutoRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import br.com.inova.sigin.shared.service.GeradorCodigoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final GeradorCodigoService geradorCodigoService;

    @Transactional
    public Produto salvar(Produto produto) {

        if (produto.getCodigo() != null) {
            throw new RegraNegocioException("O código é gerado automaticamente.");
        }

        Produto salvo = produtoRepository.save(produto);

        salvo.setCodigo(
                geradorCodigoService.gerarCodigoProduto(salvo.getId())
        );

        return produtoRepository.save(salvo);
    }

}