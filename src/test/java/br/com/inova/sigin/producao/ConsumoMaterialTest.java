package br.com.inova.sigin.producao;

import br.com.inova.sigin.apontamentoproducao.dto.ApontamentoProducaoRequest;
import br.com.inova.sigin.apontamentoproducao.service.ApontamentoProducaoService;
import br.com.inova.sigin.movimentacaoestoque.entity.MovimentacaoEstoque;
import br.com.inova.sigin.movimentacaoestoque.repository.MovimentacaoEstoqueRepository;
import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import br.com.inova.sigin.shared.BaseIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

class ConsumoMaterialTest extends BaseIntegrationTest {

    @Autowired
    private ApontamentoProducaoService apontamentoService;

    @Autowired
    private MovimentacaoEstoqueRepository movimentacaoRepository;

    @Test
    void deveConsumirMaterialCorretamente() {

        OrdemProducao op = testFactory.criarOrdemProducao();

        ApontamentoProducaoRequest request = new ApontamentoProducaoRequest();
        request.setOrdemProducaoId(op.getId());
        request.setQuantidadeProduzida(BigDecimal.TEN);
        request.setQuantidadePerda(BigDecimal.ZERO);

        apontamentoService.criar(request);

        MovimentacaoEstoque consumo =
                movimentacaoRepository
                        .findByOrigemAndReferenciaId(
                                "OP",
                                op.getId()
                        )
                        .stream()
                        .filter(m -> "CONSUMO_PRODUCAO".equals(m.getTipo()))
                        .findFirst()
                        .orElseThrow();
    }
}