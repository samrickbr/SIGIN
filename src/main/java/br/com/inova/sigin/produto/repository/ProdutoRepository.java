package br.com.inova.sigin.produto.repository;

import br.com.inova.sigin.produto.entity.Produto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<Produto> findByAtivoTrue();

    List<Produto> findByAtivoFalse();

    @EntityGraph(attributePaths = "categoria")
    List<Produto> findByAtivoTrueAndDisponivelVendaTrue();

    @Query("""
            select p 
            from Produto p
            left join fetch p.categoria
            left join fetch p.vendas
            where p.ativo = true
                and exists (
                    select 1
                    from ProdutoVenda pv
                    where pv.produto = p
                      and pv.disponivelVenda = true
                )
            """)
    List<Produto> buscarCardapio();

    Optional<Produto> findByNome(String nome);

    @Query("""
                SELECT pv.produto
                FROM ProdutoVenda pv
                WHERE pv.disponivelVenda = true
                AND pv.produto.ativo = true
            """)
    List<Produto> findProdutosDisponiveisVenda();
}