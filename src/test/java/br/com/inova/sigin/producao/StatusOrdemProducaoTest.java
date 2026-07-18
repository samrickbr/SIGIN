package br.com.inova.sigin.producao;

import br.com.inova.sigin.apontamentoproducao.dto.ApontamentoProducaoRequest;
import br.com.inova.sigin.apontamentoproducao.service.ApontamentoProducaoService;
import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import br.com.inova.sigin.ordemproducao.enums.StatusOrdemProducao;
import br.com.inova.sigin.ordemproducao.repository.OrdemProducaoRepository;
import br.com.inova.sigin.shared.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusOrdemProducaoTest extends BaseIntegrationTest {

    @Autowired
    private ApontamentoProducaoService apontamentoService;

    @Autowired
    private OrdemProducaoRepository ordemProducaoRepository;

    @Test
    void deveColocarOrdemEmProducaoAoApontar() {

        OrdemProducao op = testFactory.criarOrdemProducao();

        assertEquals(
                StatusOrdemProducao.ABERTA,
                op.getStatus()
        );

        ApontamentoProducaoRequest request =
                new ApontamentoProducaoRequest();

        request.setOrdemProducaoId(op.getId());
        request.setQuantidadeProduzida(BigDecimal.ONE);
        request.setQuantidadePerda(BigDecimal.ZERO);

        apontamentoService.criar(request);

        OrdemProducao atualizada =
                ordemProducaoRepository.findById(op.getId())
                        .orElseThrow();

        assertEquals(
                StatusOrdemProducao.EM_PRODUCAO,
                atualizada.getStatus()
        );
    }
}