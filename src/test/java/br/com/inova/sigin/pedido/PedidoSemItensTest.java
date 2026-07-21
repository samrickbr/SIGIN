package br.com.inova.sigin.pedido;

import br.com.inova.sigin.pedido.entity.Pedido;
import br.com.inova.sigin.pedido.service.PedidoService;
import br.com.inova.sigin.shared.BaseIntegrationTest;
import br.com.inova.sigin.shared.TestFactory;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PedidoSemItensTest extends BaseIntegrationTest {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private TestFactory testFactory;

    @Test
    void naoDeveGerarOrdemParaPedidoSemItens() {

        Pedido pedido = testFactory.criarPedidoSemItens();

        assertThrows(
                RegraNegocioException.class,
                () -> pedidoService.gerarOrdemProducao(pedido.getId())
        );
    }
}