package br.com.inova.sigin.produtocanal.repository;

import br.com.inova.sigin.produtocanal.entity.ProdutoCanal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoCanalRepository extends JpaRepository<ProdutoCanal, Long> {
    List<ProdutoCanal> findByProdutoId(Long produtoId);
    List<ProdutoCanal> findByCanalVendaId(Long canalVendaId);
    Optional<ProdutoCanal> findByProdutoIdAndCanalVendaId(Long produtoId, Long canalVendaId);
    boolean existsByProdutoIdAndCanalVendaId(Long produtoId, Long canalVendaId);
    boolean existsByProdutoIdAndCanalVendaIdAndAtivoTrue(
            Long produtoId,
            Long canalVendaId
    );
}