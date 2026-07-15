package br.com.inova.sigin.apontamentoproducao.service;

import br.com.inova.sigin.apontamentoproducao.dto.ApontamentoProducaoRequest;
import br.com.inova.sigin.apontamentoproducao.dto.ApontamentoProducaoResponse;
import br.com.inova.sigin.apontamentoproducao.dto.ApontamentoProducaoUpdateRequest;
import br.com.inova.sigin.apontamentoproducao.entity.ApontamentoProducao;
import br.com.inova.sigin.apontamentoproducao.mapper.ApontamentoProducaoMapper;
import br.com.inova.sigin.apontamentoproducao.repository.ApontamentoProducaoRepository;
import br.com.inova.sigin.local.entity.Local;
import br.com.inova.sigin.local.repository.LocalRepository;
import br.com.inova.sigin.movimentacaoestoque.service.MovimentacaoEstoqueService;
import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import br.com.inova.sigin.ordemproducao.repository.OrdemProducaoRepository;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.produtomaterial.entity.ProdutoMaterial;
import br.com.inova.sigin.produtomaterial.repository.ProdutoMaterialRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApontamentoProducaoService {

    private final ApontamentoProducaoRepository repository;
    private final OrdemProducaoRepository ordemProducaoRepository;
    private final PessoaRepository pessoaRepository;
    private final ApontamentoProducaoMapper mapper;
    private final ProdutoMaterialRepository produtoMaterialRepository;
    private final LocalRepository localRepository;
    private final MovimentacaoEstoqueService movimentacaoEstoqueService;

    public ApontamentoProducaoResponse criar(
            ApontamentoProducaoRequest request) {
        OrdemProducao ordemProducao =
                ordemProducaoRepository.findById(request.getOrdemProducaoId())
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Ordem de produção não encontrada"
                                ));
        Pessoa responsavel = null;
        if (request.getResponsavelId() != null) {
            responsavel = pessoaRepository.findById(
                    request.getResponsavelId()
            ).orElseThrow(() ->
                    new RegraNegocioException(
                            "Responsável não encontrado"
                    ));
        }
        ApontamentoProducao entity =
                ApontamentoProducao.builder()
                        .ordemProducao(ordemProducao)
                        .quantidadeProduzida(
                                request.getQuantidadeProduzida()
                        )
                        .quantidadePerda(
                                request.getQuantidadePerda()
                        )
                        .responsavel(responsavel)
                        .observacao(request.getObservacao())
                        .dataApontamento(LocalDateTime.now())
                        .ativo(true)
                        .build();
        atualizarQuantidadeProduzida(
                ordemProducao,
                request.getQuantidadeProduzida()
        );
        ApontamentoProducao salvo = repository.save(entity);

        atualizarQuantidadeProduzida(
                ordemProducao,
                salvo.getQuantidadeProduzida()
        );
        gerarConsumoMateriais(
                ordemProducao,
                salvo
        );
        gerarEntradaProduto(
                ordemProducao,
                salvo
        );
        return mapper.toResponse(salvo);
    }
    private void gerarEntradaProduto(
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


    public List<ApontamentoProducaoResponse> listarPorOp(
            Long ordemProducaoId) {
        return repository.findByOrdemProducaoId(ordemProducaoId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ApontamentoProducaoResponse buscarPorId(Long id) {
        ApontamentoProducao entity =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Apontamento não encontrado"
                                ));
        return mapper.toResponse(entity);
    }
    public ApontamentoProducaoResponse atualizar(
            Long id,
            ApontamentoProducaoUpdateRequest request) {
        ApontamentoProducao entity =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Apontamento não encontrado"
                                ));
        if (request.getQuantidadeProduzida() != null) {
            entity.setQuantidadeProduzida(
                    request.getQuantidadeProduzida()
            );
        }
        if (request.getQuantidadePerda() != null) {
            entity.setQuantidadePerda(
                    request.getQuantidadePerda()
            );
        }
        if (request.getObservacao() != null) {
            entity.setObservacao(
                    request.getObservacao()
            );
        }
        if (request.getAtivo() != null) {
            entity.setAtivo(
                    request.getAtivo()
            );
        }
        return mapper.toResponse(
                repository.save(entity)
        );
    }

    public void excluir(Long id) {

        ApontamentoProducao entity =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Apontamento não encontrado"
                                ));
        entity.setAtivo(false);
        repository.save(entity);
    }

    private void atualizarQuantidadeProduzida(
            OrdemProducao ordemProducao,
            BigDecimal quantidade) {
        BigDecimal atual =
                ordemProducao.getQuantidadeProduzida();
        ordemProducao.setQuantidadeProduzida(
                atual.add(quantidade)
        );
        ordemProducaoRepository.save(ordemProducao);
    }

    private void gerarConsumoMateriais(
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