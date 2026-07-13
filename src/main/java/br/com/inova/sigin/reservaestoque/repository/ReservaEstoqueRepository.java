package br.com.inova.sigin.reservaestoque.repository;

import br.com.inova.sigin.reservaestoque.entity.ReservaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ReservaEstoqueRepository
        extends JpaRepository<ReservaEstoque, Long> {

    List<ReservaEstoque> findByOrdemProducaoId(Long ordemProducaoId);

    List<ReservaEstoque> findByMaterialId(Long materialId);

    boolean existsByOrdemProducaoId(Long ordemProducaoId);

    @Query("""
            SELECT COALESCE(SUM(r.quantidade), 0)
            FROM ReservaEstoque r
            WHERE r.material.id = :materialId
            AND r.ativo = true
            """)
    BigDecimal calcularQuantidadeReservada(
            Long materialId
    );
}