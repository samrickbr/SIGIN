package br.com.inova.sigin.produtovenda.repository;

import br.com.inova.sigin.produtovenda.entity.ProdutoVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoVendaRepository
        extends JpaRepository<ProdutoVenda, Long> {
    Optional<ProdutoVenda> findByProdutoId(Long produtoId);
    @Query("""
    SELECT pv
    FROM ProdutoVenda pv
    JOIN FETCH pv.produto p
    LEFT JOIN FETCH p.categoria
    WHERE pv.disponivelVenda = true
    AND p.ativo = true
""")
    List<ProdutoVenda> findProdutosDisponiveis();

}