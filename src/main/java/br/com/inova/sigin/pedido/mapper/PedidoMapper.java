package br.com.inova.sigin.pedido.mapper;

import br.com.inova.sigin.pedido.dto.PedidoResponse;
import br.com.inova.sigin.pedido.entity.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public PedidoResponse toResponse(Pedido pedido) {

        return PedidoResponse.builder()
                .id(pedido.getId())
                .numero(pedido.getNumero())
                .clienteId(pedido.getCliente().getId())
                .cliente(pedido.getCliente().getNome())
                .dataPedido(pedido.getDataPedido())
                .valorTotal(pedido.getValorTotal())
                .status(pedido.getStatus().name())
                .ativo(pedido.getAtivo())
                .observacao(pedido.getObservacao())
                .build();
    }
}