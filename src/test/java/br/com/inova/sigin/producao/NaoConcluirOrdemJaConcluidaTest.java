package br.com.inova.sigin.ordemproducao;

import br.com.inova.sigin.apontamentoproducao.dto.ApontamentoProducaoRequest;
import br.com.inova.sigin.apontamentoproducao.service.ApontamentoProducaoService;
import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import br.com.inova.sigin.ordemproducao.service.OrdemProducaoService;
import br.com.inova.sigin.shared.TestFactory;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class NaoConcluirOrdemJaConcluidaTest {

    @Autowired
    private TestFactory testFactory;

    @Autowired
    private ApontamentoProducaoService apontamentoService;

    @Autowired
    private OrdemProducaoService ordemProducaoService;

    @Test
    void naoDeveConcluirOrdemJaConcluida() {

        OrdemProducao op =
                testFactory.criarOrdemProducao();

        ApontamentoProducaoRequest request =
                new ApontamentoProducaoRequest();

        request.setOrdemProducaoId(op.getId());
        request.setQuantidadeProduzida(BigDecimal.TEN);
        request.setQuantidadePerda(BigDecimal.ZERO);

        apontamentoService.criar(request);

        ordemProducaoService.concluir(op.getId());

        assertThrows(
                RegraNegocioException.class,
                () -> ordemProducaoService.concluir(op.getId())
        );
    }
}