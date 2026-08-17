package br.com.inova.sigin.produtovenda.repository;

import br.com.inova.sigin.produtovenda.entity.ProdutoVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProdutoVendaRepository extends JpaRepository<ProdutoVenda, Long> {

    List<ProdutoVenda> findByProdutoId(Long produtoId);

    Optional<ProdutoVenda> findByProdutoIdAndCanalVendaId(
            Long produtoId,
            Long canalVendaId
    );
    boolean existsByProdutoIdAndCanalVendaId(
            Long produtoId,
            Long canalVendaId
    );
    List<ProdutoVenda> findByCanalVendaIdAndDisponivelVendaTrue(
            Long canalVendaId
    );
    @Query("""
            SELECT pv
            FROM ProdutoVenda pv
            WHERE pv.disponivelVenda = true
            """)
    List<ProdutoVenda> findProdutosDisponiveis();

    @Query("""
    SELECT pv
    FROM ProdutoVenda pv
    JOIN FETCH pv.produto p
    JOIN FETCH pv.canalVenda c
    WHERE c.id = :canalVendaId
    AND c.ativo = true
    AND pv.disponivelVenda = true
    AND EXISTS (
        SELECT 1
        FROM ProdutoCanal pc
        WHERE pc.produto = p
        AND pc.canalVenda = c
        AND pc.ativo = true
    )
    """)
    List<ProdutoVenda> findCatalogoPorCanal(
            @Param("canalVendaId") Long canalVendaId
    );
}