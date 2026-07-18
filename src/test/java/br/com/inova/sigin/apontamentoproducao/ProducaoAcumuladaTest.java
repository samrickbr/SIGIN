package br.com.inova.sigin.apontamentoproducao;

import br.com.inova.sigin.apontamentoproducao.dto.ApontamentoProducaoRequest;
import br.com.inova.sigin.apontamentoproducao.service.ApontamentoProducaoService;
import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import br.com.inova.sigin.ordemproducao.enums.StatusOrdemProducao;
import br.com.inova.sigin.ordemproducao.repository.OrdemProducaoRepository;
import br.com.inova.sigin.shared.TestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProducaoAcumuladaTest {

    @Autowired
    private TestFactory testFactory;
    @Autowired
    private ApontamentoProducaoService apontamentoService;

    @Autowired
    private OrdemProducaoRepository ordemProducaoRepository;

    @Test
    void deveAcumularProducaoEmMultiplosApontamentos() {

        OrdemProducao op =
                testFactory.criarOrdemProducao(
                        BigDecimal.valueOf(100)
                );

        criarApontamento(op.getId(), BigDecimal.valueOf(20));

        criarApontamento(op.getId(), BigDecimal.valueOf(30));


        OrdemProducao atualizada =
                ordemProducaoRepository.findById(op.getId())
                        .orElseThrow();


        assertEquals(
                0,
                BigDecimal.valueOf(50)
                        .compareTo(
                                atualizada.getQuantidadeProduzida()
                        )
        );


        assertEquals(
                StatusOrdemProducao.EM_PRODUCAO,
                atualizada.getStatus()
        );
    }

    private void criarApontamento(
            Long ordemId,
            BigDecimal quantidade) {

        ApontamentoProducaoRequest request =
                new ApontamentoProducaoRequest();

        request.setOrdemProducaoId(ordemId);
        request.setQuantidadeProduzida(quantidade);
        request.setQuantidadePerda(BigDecimal.ZERO);

        apontamentoService.criar(request);
    }
}
