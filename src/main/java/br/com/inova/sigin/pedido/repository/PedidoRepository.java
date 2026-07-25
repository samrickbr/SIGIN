package br.com.inova.sigin.pedido.repository;

import br.com.inova.sigin.pedido.entity.Pedido;
import br.com.inova.sigin.pedido.enums.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    boolean existsByNumero(String numero);

    Optional<Pedido> findByNumero(String numero);

    List<Pedido> findByStatus(StatusPedido status);
}