package br.com.inova.sigin.apontamentoproducao;

import br.com.inova.sigin.apontamentoproducao.dto.ApontamentoProducaoRequest;
import br.com.inova.sigin.apontamentoproducao.service.ApontamentoProducaoService;
import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import br.com.inova.sigin.ordemproducao.enums.StatusOrdemProducao;
import br.com.inova.sigin.ordemproducao.repository.OrdemProducaoRepository;
import br.com.inova.sigin.shared.TestFactory;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class NaoApontarProducaoEmOrdemCanceladaTest {

    @Autowired
    private TestFactory testFactory;

    @Autowired
    private ApontamentoProducaoService apontamentoService;

    @Autowired
    private OrdemProducaoRepository ordemProducaoRepository;

    @Test
    void naoDevePermitirApontamentoEmOrdemCancelada() {

        OrdemProducao op = testFactory.criarOrdemProducao();

        op.setStatus(StatusOrdemProducao.CANCELADA);

        ordemProducaoRepository.save(op);

        ApontamentoProducaoRequest request =
                new ApontamentoProducaoRequest();

        request.setOrdemProducaoId(op.getId());
        request.setQuantidadeProduzida(BigDecimal.TEN);
        request.setQuantidadePerda(BigDecimal.ZERO);

        assertThrows(
                RegraNegocioException.class,
                () -> apontamentoService.criar(request)
        );
    }
}