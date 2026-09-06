package br.com.inova.sigin.pedido;

import br.com.inova.sigin.canalvenda.entity.CanalVenda;
import br.com.inova.sigin.pedido.dto.PedidoResponse;
import br.com.inova.sigin.pedido.entity.Pedido;
import br.com.inova.sigin.pedido.entity.PedidoItem;
import br.com.inova.sigin.pedido.enums.StatusPedido;
import br.com.inova.sigin.pedido.enums.TipoRecebimento;
import br.com.inova.sigin.pedido.mapper.PedidoMapper;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.produto.entity.Produto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PedidoMapperTest {

    @Test
    void deveCalcularValorProdutosSomenteComItensAtivos() {
        Pedido pedido = mock(Pedido.class);
        PedidoItem itemInativo = item("20", false);
        PedidoItem itemAtivo = item("30", true);
        Pessoa cliente = mock(Pessoa.class);
        CanalVenda canalVenda = mock(CanalVenda.class);

        when(pedido.getItens()).thenReturn(List.of(itemInativo, itemAtivo));
        when(pedido.getCliente()).thenReturn(cliente);
        when(cliente.getId()).thenReturn(1L);
        when(cliente.getNome()).thenReturn("Cliente");
        when(pedido.getTipoRecebimento()).thenReturn(TipoRecebimento.RETIRADA);
        when(pedido.getCanalVenda()).thenReturn(canalVenda);
        when(canalVenda.getId()).thenReturn(1L);
        when(canalVenda.getNome()).thenReturn("Balcao");
        when(pedido.getDataPedido()).thenReturn(LocalDateTime.now());
        when(pedido.getPagamentos()).thenReturn(List.of());
        when(pedido.getStatus()).thenReturn(StatusPedido.ABERTO);
        when(pedido.getAtivo()).thenReturn(true);
        when(pedido.getTaxaEntrega()).thenReturn(BigDecimal.ZERO);
        when(pedido.getValorTotal()).thenReturn(new BigDecimal("30"));

        PedidoResponse response = new PedidoMapper().toResponse(pedido);

        assertEquals(new BigDecimal("30"), response.getValorProdutos());
    }

    private PedidoItem item(String valorTotal, boolean ativo) {
        PedidoItem item = new PedidoItem();
        Produto produto = mock(Produto.class);
        when(produto.getId()).thenReturn(1L);
        when(produto.getNome()).thenReturn("Produto");
        item.setProduto(produto);
        item.setValorTotal(new BigDecimal(valorTotal));
        item.setAtivo(ativo);
        return item;
    }
}
