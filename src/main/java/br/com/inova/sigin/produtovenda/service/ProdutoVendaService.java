package br.com.inova.sigin.produtovenda.service;

import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produtovenda.entity.ProdutoVenda;
import br.com.inova.sigin.produtovenda.repository.ProdutoVendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoVendaService {
    private final ProdutoVendaRepository repository;

    @Transactional(readOnly = true)
    public List<ProdutoVenda> listarDisponiveis() {
        return repository.findProdutosDisponiveis();
    }
}