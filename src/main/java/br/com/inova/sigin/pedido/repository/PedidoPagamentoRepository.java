package br.com.inova.sigin.pedido.repository;

import br.com.inova.sigin.pedido.entity.PedidoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoPagamentoRepository
        extends JpaRepository<PedidoPagamento, Long> {
}