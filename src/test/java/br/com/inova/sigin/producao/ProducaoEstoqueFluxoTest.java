package br.com.inova.sigin.producao;

import br.com.inova.sigin.dev.dto.FluxoPedidoResponse;
import br.com.inova.sigin.fluxo.service.FluxoPedidoService;
import br.com.inova.sigin.movimentacaoestoque.repository.MovimentacaoEstoqueRepository;
import br.com.inova.sigin.shared.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class ProducaoEstoqueFluxoTest extends BaseIntegrationTest {

    @Autowired
    private FluxoPedidoService fluxoPedidoService;

    @Autowired
    private MovimentacaoEstoqueRepository movimentacaoRepository;

    @Test
    void deveConsumirMaterialEGerarProdutoFinal() {

        FluxoPedidoResponse fluxo = fluxoPedidoService.executar();

        fluxoPedidoService.produzir(
                fluxo.getOrdensProducao().getFirst()
        );

        assertTrue(
                movimentacaoRepository.count() >= 2
        );
    }
}