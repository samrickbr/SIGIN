package br.com.inova.sigin.producao;

import br.com.inova.sigin.pedido.entity.Pedido;
import br.com.inova.sigin.pedido.service.PedidoService;
import br.com.inova.sigin.shared.BaseIntegrationTest;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ProdutoSemFichaTecnicaTest extends BaseIntegrationTest {

    @Autowired
    private PedidoService pedidoService;

    @Test
    void naoDeveGerarOpSemFichaTecnica() {

        Pedido pedido = testFactory.criarPedidoComProdutoSemFicha();

        assertThrows(
                RegraNegocioException.class,
                () -> pedidoService.gerarOrdemProducao(pedido.getId())
        );
    }
}