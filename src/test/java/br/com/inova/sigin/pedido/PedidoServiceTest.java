package br.com.inova.sigin.pedido;

import br.com.inova.sigin.pedido.dto.PedidoResponse;
import br.com.inova.sigin.pedido.entity.Pedido;
import br.com.inova.sigin.pedido.entity.PedidoItem;
import br.com.inova.sigin.pedido.enums.StatusPedido;
import br.com.inova.sigin.pedido.mapper.PedidoMapper;
import br.com.inova.sigin.pedido.repository.PedidoRepository;
import br.com.inova.sigin.pedido.service.PedidoService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PedidoServiceTest {

    @Test
    void deveInativarItensAoCancelarPedido() {
        PedidoRepository repository = mock(PedidoRepository.class);
        PedidoMapper mapper = mock(PedidoMapper.class);
        PedidoService service = new PedidoService(
                repository, null, mapper, null, null, null, null,
                null, null, null, null, null
        );
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setStatus(StatusPedido.ABERTO);
        pedido.setTaxaEntrega(BigDecimal.ZERO);
        PedidoItem primeiroItem = item(new BigDecimal("20"));
        PedidoItem segundoItem = item(new BigDecimal("30"));
        pedido.setItens(List.of(primeiroItem, segundoItem));

        when(repository.findById(1L)).thenReturn(Optional.of(pedido));
        when(repository.save(pedido)).thenReturn(pedido);
        when(mapper.toResponse(pedido))
                .thenReturn(PedidoResponse.builder().build());

        service.cancelar(1L);

        assertEquals(StatusPedido.CANCELADO, pedido.getStatus());
        assertFalse(primeiroItem.getAtivo());
        assertFalse(segundoItem.getAtivo());
        assertEquals(BigDecimal.ZERO, pedido.getValorTotal());
    }

    private PedidoItem item(BigDecimal valorTotal) {
        PedidoItem item = new PedidoItem();
        item.setValorTotal(valorTotal);
        item.setAtivo(true);
        return item;
    }
}
