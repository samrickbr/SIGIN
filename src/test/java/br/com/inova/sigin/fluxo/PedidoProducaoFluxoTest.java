package br.com.inova.sigin.fluxo;

import br.com.inova.sigin.dev.dto.FluxoPedidoResponse;
import br.com.inova.sigin.fluxo.service.FluxoPedidoService;
import br.com.inova.sigin.ordemproducao.dto.OrdemProducaoResponse;
import br.com.inova.sigin.pedido.entity.Pedido;
import br.com.inova.sigin.pedido.service.PedidoService;
import br.com.inova.sigin.shared.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoProducaoFluxoTest extends BaseIntegrationTest {

    @Autowired
    private FluxoPedidoService fluxoPedidoService;

    @Autowired
    private PedidoService pedidoService;

    @Test
    void deveGerarPedidoEOrdemProducao() {

        Pedido pedido = testFactory.criarPedidoComItens();

        List<OrdemProducaoResponse> ordens =
                pedidoService.gerarOrdemProducao(pedido.getId());

        assertEquals(1, ordens.size());
        assertEquals("ABERTA", ordens.getFirst().getStatus());
    }
}