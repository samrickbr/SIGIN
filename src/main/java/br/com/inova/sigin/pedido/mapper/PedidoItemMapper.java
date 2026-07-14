package br.com.inova.sigin.pedido.mapper;

import br.com.inova.sigin.pedido.dto.PedidoItemResponse;
import br.com.inova.sigin.pedido.entity.PedidoItem;
import org.springframework.stereotype.Component;

@Component
public class PedidoItemMapper {

    public PedidoItemResponse toResponse(PedidoItem item) {

        return PedidoItemResponse.builder()
                .id(item.getId())
                .produtoId(item.getProduto().getId())
                .produto(item.getProduto().getNome())
                .quantidade(item.getQuantidade())
                .valorUnitario(item.getValorUnitario())
                .valorTotal(item.getValorTotal())
                .build();
    }
}