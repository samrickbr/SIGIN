package br.com.inova.sigin.pedido.repository;

import br.com.inova.sigin.pedido.entity.Pedido;
import br.com.inova.sigin.pedido.enums.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    boolean existsByNumero(String numero);

    Optional<Pedido> findByNumero(String numero);

    @Query("""
                SELECT DISTINCT p
                FROM Pedido p
                JOIN FETCH p.cliente c
                LEFT JOIN FETCH p.itens i
                LEFT JOIN FETCH i.produto
                WHERE p.status = :status
            """)
    List<Pedido> findByStatus(@Param("status") StatusPedido status);

    List<Pedido> findByStatusIn(List<StatusPedido> status);
}