package br.com.inova.sigin.pedido.repository;

import br.com.inova.sigin.pedido.entity.PedidoEndereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PedidoEnderecoRepository
        extends JpaRepository<PedidoEndereco, Long> {

    Optional<PedidoEndereco> findByPedidoId(Long pedidoId);
}