package br.com.inova.sigin.opmaterial.service;

import br.com.inova.sigin.apontamentoproducao.entity.ApontamentoProducao;
import br.com.inova.sigin.local.entity.Local;
import br.com.inova.sigin.local.repository.LocalRepository;
import br.com.inova.sigin.movimentacaoestoque.service.MovimentacaoEstoqueService;
import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import br.com.inova.sigin.produtomaterial.entity.ProdutoMaterial;
import br.com.inova.sigin.produtomaterial.repository.ProdutoMaterialRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsumoMaterialProducaoService {
    private final ProdutoMaterialRepository produtoMaterialRepository;
    private final LocalRepository localRepository;
    private final MovimentacaoEstoqueService movimentacaoEstoqueService;

    public void gerarConsumo(
            OrdemProducao ordemProducao,
            ApontamentoProducao apontamento) {

        BigDecimal quantidadeProduzida =
                apontamento.getQuantidadeProduzida();

        List<ProdutoMaterial> materiais =
                produtoMaterialRepository.findByProdutoId(
                        ordemProducao.getProduto().getId()
                );

        Local local = localRepository.findById(
                ordemProducao.getLocalDestino().getId()
        ).orElseThrow(() ->
                new RegraNegocioException(
                        "Local não encontrado"
                ));

        for (ProdutoMaterial produtoMaterial : materiais) {

            BigDecimal consumo =
                    produtoMaterial.getQuantidade()
                            .multiply(quantidadeProduzida);

            movimentacaoEstoqueService.registrarMovimentacao(
                    produtoMaterial.getMaterial(),
                    null,
                    local,
                    "CONSUMO_PRODUCAO",
                    "SAIDA",
                    consumo,
                    "OP",
                    ordemProducao.getId(),
                    apontamento.getResponsavel(),
                    "Consumo automático da OP " + ordemProducao.getNumero()
            );
        }
    }

}