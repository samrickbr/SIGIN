package br.com.inova.sigin.producao.service;

import br.com.inova.sigin.apontamentoproducao.entity.ApontamentoProducao;
import br.com.inova.sigin.local.entity.Local;
import br.com.inova.sigin.local.repository.LocalRepository;
import br.com.inova.sigin.movimentacaoestoque.service.MovimentacaoEstoqueService;
import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntradaProdutoProducaoService {

    private final LocalRepository localRepository;
    private final MovimentacaoEstoqueService movimentacaoEstoqueService;
    public void gerarEntrada(
            OrdemProducao ordemProducao,
            ApontamentoProducao apontamento) {

        Local local = localRepository.findById(
                ordemProducao.getLocalDestino().getId()
        ).orElseThrow(() ->
                new RegraNegocioException(
                        "Local não encontrado"
                ));

        movimentacaoEstoqueService.registrarMovimentacao(
                null,
                ordemProducao.getProduto(),
                local,
                "PRODUCAO",
                "ENTRADA",
                apontamento.getQuantidadeProduzida(),
                "OP",
                ordemProducao.getId(),
                apontamento.getResponsavel(),
                "Entrada automática produção OP "
                        + ordemProducao.getNumero()
        );
    }
}