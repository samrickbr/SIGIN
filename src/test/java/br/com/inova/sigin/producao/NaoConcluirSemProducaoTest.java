package br.com.inova.sigin.ordemproducao;

import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import br.com.inova.sigin.ordemproducao.service.OrdemProducaoService;
import br.com.inova.sigin.shared.TestFactory;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class NaoConcluirSemProducaoTest {

    @Autowired
    private TestFactory testFactory;

    @Autowired
    private OrdemProducaoService ordemProducaoService;

    @Test
    void naoDeveConcluirOrdemSemProducao() {

        OrdemProducao op =
                testFactory.criarOrdemProducao();

        assertThrows(
                RegraNegocioException.class,
                () -> ordemProducaoService.concluir(op.getId())
        );
    }
}