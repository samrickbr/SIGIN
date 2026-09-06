package br.com.inova.sigin.pedido;

import br.com.inova.sigin.pedido.dto.PedidoItemResponse;
import br.com.inova.sigin.pedido.entity.Pedido;
import br.com.inova.sigin.pedido.entity.PedidoItem;
import br.com.inova.sigin.pedido.enums.StatusPedido;
import br.com.inova.sigin.pedido.mapper.PedidoItemMapper;
import br.com.inova.sigin.pedido.repository.PedidoItemRepository;
import br.com.inova.sigin.pedido.repository.PedidoRepository;
import br.com.inova.sigin.pedido.service.PedidoItemService;
import br.com.inova.sigin.produto.entity.Produto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PedidoItemServiceTest {

    @Test
    void deveAlterarSomenteItemIdentificadoPeloId() {
        PedidoItemRepository itemRepository =
                mock(PedidoItemRepository.class);
        PedidoRepository pedidoRepository =
                mock(PedidoRepository.class);
        PedidoItemMapper mapper = mock(PedidoItemMapper.class);
        PedidoItemService service = new PedidoItemService(
                itemRepository, pedidoRepository, null, mapper, null
        );

        Pedido pedido = pedidoComTotal(BigDecimal.ZERO);
        Produto produto = mock(Produto.class);
        PedidoItem primeiroItem = item(10L, pedido, produto, "2", true);
        PedidoItem segundoItem = item(11L, pedido, produto, "3", true);

        when(pedidoRepository.findById(1L))
                .thenReturn(Optional.of(pedido));
        when(itemRepository.findById(10L))
                .thenReturn(Optional.of(primeiroItem));
        when(itemRepository.findByPedidoId(1L))
                .thenReturn(List.of(primeiroItem, segundoItem));
        when(itemRepository.save(any(PedidoItem.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toResponse(primeiroItem))
                .thenReturn(PedidoItemResponse.builder().id(10L).build());

        PedidoItemResponse response = service.alterarQuantidade(
                1L, 10L, new BigDecimal("5")
        );

        assertEquals(10L, response.getId());
        assertEquals(new BigDecimal("5"), primeiroItem.getQuantidade());
        assertEquals(new BigDecimal("50"), primeiroItem.getValorTotal());
        assertEquals(new BigDecimal("3"), segundoItem.getQuantidade());
        assertEquals(new BigDecimal("30"), segundoItem.getValorTotal());
        assertEquals(new BigDecimal("80"), pedido.getValorTotal());
    }

    @Test
    void deveInativarSomenteItemIdentificadoPeloIdERecalcularTotal() {
        PedidoItemRepository itemRepository =
                mock(PedidoItemRepository.class);
        PedidoRepository pedidoRepository =
                mock(PedidoRepository.class);
        PedidoItemService service = new PedidoItemService(
                itemRepository, pedidoRepository, null, null, null
        );

        Pedido pedido = pedidoComTotal(BigDecimal.ZERO);
        Produto produto = mock(Produto.class);
        PedidoItem primeiroItem = item(10L, pedido, produto, "2", true);
        PedidoItem segundoItem = item(11L, pedido, produto, "3", true);

        when(pedidoRepository.findById(1L))
                .thenReturn(Optional.of(pedido));
        when(itemRepository.findById(10L))
                .thenReturn(Optional.of(primeiroItem));
        when(itemRepository.findByPedidoId(1L))
                .thenReturn(List.of(primeiroItem, segundoItem));

        service.remover(1L, 10L);

        assertFalse(primeiroItem.getAtivo());
        assertTrue(segundoItem.getAtivo());
        assertEquals(new BigDecimal("30"), pedido.getValorTotal());
    }

    private Pedido pedidoComTotal(BigDecimal taxaEntrega) {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setStatus(StatusPedido.ABERTO);
        pedido.setTaxaEntrega(taxaEntrega);
        pedido.setValorTotal(BigDecimal.ZERO);
        return pedido;
    }

    private PedidoItem item(
            Long id,
            Pedido pedido,
            Produto produto,
            String quantidade,
            boolean ativo
    ) {
        PedidoItem item = new PedidoItem();
        item.setId(id);
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(new BigDecimal(quantidade));
        item.setValorUnitario(BigDecimal.TEN);
        item.setValorTotal(new BigDecimal(quantidade).multiply(BigDecimal.TEN));
        item.setAtivo(ativo);
        return item;
    }
}
