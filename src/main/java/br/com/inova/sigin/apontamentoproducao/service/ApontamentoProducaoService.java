package br.com.inova.sigin.apontamentoproducao.service;

import br.com.inova.sigin.apontamentoproducao.dto.ApontamentoProducaoRequest;
import br.com.inova.sigin.apontamentoproducao.dto.ApontamentoProducaoResponse;
import br.com.inova.sigin.apontamentoproducao.dto.ApontamentoProducaoUpdateRequest;
import br.com.inova.sigin.apontamentoproducao.entity.ApontamentoProducao;
import br.com.inova.sigin.apontamentoproducao.mapper.ApontamentoProducaoMapper;
import br.com.inova.sigin.apontamentoproducao.repository.ApontamentoProducaoRepository;
import br.com.inova.sigin.opmaterial.service.ConsumoMaterialProducaoService;
import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import br.com.inova.sigin.ordemproducao.enums.StatusOrdemProducao;
import br.com.inova.sigin.ordemproducao.repository.OrdemProducaoRepository;
import br.com.inova.sigin.ordemproducao.service.StatusOrdemProducaoService;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.producao.service.EntradaProdutoProducaoService;
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
    private final ConsumoMaterialProducaoService consumoMaterialService;
    private final EntradaProdutoProducaoService entradaProdutoService;
    private final StatusOrdemProducaoService statusService;


    public ApontamentoProducaoResponse criar(
            ApontamentoProducaoRequest request) {

        OrdemProducao ordemProducao = buscarOrdem(request);

        validarStatus(ordemProducao);

        validarQuantidades(request);

        Pessoa responsavel = buscarResponsavel(request);

        ApontamentoProducao salvo =
                salvarApontamento(
                        request,
                        ordemProducao,
                        responsavel
                );

        atualizarQuantidadeProduzida(
                ordemProducao,
                salvo.getQuantidadeProduzida()
        );

        consumoMaterialService.gerarConsumo(
                ordemProducao,
                salvo
        );

        statusService.atualizar(ordemProducao);

        entradaProdutoService.gerarEntrada(
                ordemProducao,
                salvo
        );

        return mapper.toResponse(salvo);
    }

    private ApontamentoProducao salvarApontamento(
            ApontamentoProducaoRequest request,
            OrdemProducao ordemProducao,
            Pessoa responsavel) {

        ApontamentoProducao entity =
                ApontamentoProducao.builder()
                        .ordemProducao(ordemProducao)
                        .quantidadeProduzida(request.getQuantidadeProduzida())
                        .quantidadePerda(request.getQuantidadePerda())
                        .responsavel(responsavel)
                        .observacao(request.getObservacao())
                        .dataApontamento(LocalDateTime.now())
                        .ativo(true)
                        .build();

        return repository.save(entity);
    }

    public List<ApontamentoProducaoResponse> listarPorOp(
            Long ordemProducaoId) {
        return repository.findByOrdemProducaoId(ordemProducaoId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ApontamentoProducaoResponse buscarPorId(Long id) {

        ApontamentoProducao entity = buscarApontamento(id);

        return mapper.toResponse(entity);
    }

    public ApontamentoProducaoResponse atualizar(
            Long id,
            ApontamentoProducaoUpdateRequest request) {

        ApontamentoProducao entity = buscarApontamento(id);

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

        ApontamentoProducao entity = buscarApontamento(id);

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

    private OrdemProducao buscarOrdem(
            ApontamentoProducaoRequest request) {

        return ordemProducaoRepository
                .findById(request.getOrdemProducaoId())
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Ordem de produção não encontrada"
                        ));
    }

    private Pessoa buscarResponsavel(
            ApontamentoProducaoRequest request) {

        if (request.getResponsavelId() == null) {
            return null;
        }

        return pessoaRepository.findById(
                request.getResponsavelId()
        ).orElseThrow(() ->
                new RegraNegocioException(
                        "Responsável não encontrado"
                ));
    }

    private void validarStatus(
            OrdemProducao ordemProducao) {

        if (ordemProducao.getStatus() == StatusOrdemProducao.CONCLUIDA
                || ordemProducao.getStatus() == StatusOrdemProducao.CANCELADA) {
            throw new RegraNegocioException(
                    "Não é permitido apontar produção para esta ordem."
            );
        }
    }

    private void validarQuantidades(
            ApontamentoProducaoRequest request) {

        if (request.getQuantidadeProduzida().compareTo(BigDecimal.ZERO) <= 0
                && request.getQuantidadePerda().compareTo(BigDecimal.ZERO) <= 0) {

            throw new RegraNegocioException(
                    "Informe quantidade produzida ou quantidade de perda."
            );
        }
    }


    private ApontamentoProducao buscarApontamento(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Apontamento não encontrado"
                        ));
    }
}