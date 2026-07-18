package br.com.inova.sigin.producao;

import br.com.inova.sigin.apontamentoproducao.dto.ApontamentoProducaoRequest;
import br.com.inova.sigin.apontamentoproducao.dto.ApontamentoProducaoResponse;
import br.com.inova.sigin.apontamentoproducao.service.ApontamentoProducaoService;
import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import br.com.inova.sigin.shared.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PerdaProducaoTest extends BaseIntegrationTest {

    @Autowired
    private ApontamentoProducaoService apontamentoService;

    @Test
    void deveRegistrarPerdaNaProducao() {

        OrdemProducao op = testFactory.criarOrdemProducao();

        ApontamentoProducaoRequest request = new ApontamentoProducaoRequest();
        request.setOrdemProducaoId(op.getId());
        request.setQuantidadeProduzida(BigDecimal.TEN);
        request.setQuantidadePerda(BigDecimal.valueOf(2));

        ApontamentoProducaoResponse response =
                apontamentoService.criar(request);

        assertEquals(
                0,
                BigDecimal.TEN.compareTo(response.getQuantidadeProduzida())
        );

        assertEquals(
                0,
                BigDecimal.valueOf(2).compareTo(response.getQuantidadePerda())
        );
    }
}