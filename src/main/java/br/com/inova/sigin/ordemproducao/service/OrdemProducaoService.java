package br.com.inova.sigin.ordemproducao.service;

import br.com.inova.sigin.local.entity.Local;
import br.com.inova.sigin.local.repository.LocalRepository;
import br.com.inova.sigin.ordemproducao.dto.OrdemProducaoRequest;
import br.com.inova.sigin.ordemproducao.dto.OrdemProducaoResponse;
import br.com.inova.sigin.ordemproducao.dto.OrdemProducaoUpdateRequest;
import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import br.com.inova.sigin.ordemproducao.enums.StatusOrdemProducao;
import br.com.inova.sigin.ordemproducao.mapper.OrdemProducaoMapper;
import br.com.inova.sigin.ordemproducao.repository.OrdemProducaoRepository;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produto.repository.ProdutoRepository;
import br.com.inova.sigin.reservaestoque.service.ReservaEstoqueService;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdemProducaoService {

    private final OrdemProducaoRepository repository;
    private final ProdutoRepository produtoRepository;
    private final LocalRepository localRepository;
    private final PessoaRepository pessoaRepository;
    private final OrdemProducaoMapper mapper;
    private final ReservaEstoqueService reservaEstoqueService;

    @Transactional
    public OrdemProducaoResponse criar(OrdemProducaoRequest request) {

        if (repository.existsByNumero(request.getNumero())) {
            throw new RegraNegocioException(
                    "Número da ordem de produção já cadastrado."
            );
        }

        Produto produto = produtoRepository.findById(request.getProdutoId())
                .orElseThrow(() ->
                        new RegraNegocioException("Produto não encontrado"));


        Local local = localRepository.findById(request.getLocalDestinoId())
                .orElseThrow(() ->
                        new RegraNegocioException("Local não encontrado"));


        Pessoa responsavel = null;

        if (request.getResponsavelId() != null) {
            responsavel = pessoaRepository.findById(request.getResponsavelId())
                    .orElseThrow(() ->
                            new RegraNegocioException("Responsável não encontrado"));
        }


        OrdemProducao op = OrdemProducao.builder()
                .numero(request.getNumero().toUpperCase())
                .produto(produto)
                .quantidadePlanejada(request.getQuantidadePlanejada())
                .localDestino(local)
                .responsavel(responsavel)
                .status(StatusOrdemProducao.ABERTA)
                .origem(request.getOrigem().toUpperCase())
                .observacao(request.getObservacao())
                .quantidadeProduzida(BigDecimal.ZERO)
                .ativo(true)
                .dataAbertura(LocalDateTime.now())
                .build();

        OrdemProducao ordemSalva = repository.save(op);

        return mapper.toResponse(ordemSalva);
    }


    public List<OrdemProducaoResponse> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }


    public OrdemProducaoResponse buscarPorId(Long id) {

        OrdemProducao op = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Ordem de produção não encontrada"));

        return mapper.toResponse(op);
    }


    public OrdemProducaoResponse atualizar(
            Long id,
            OrdemProducaoUpdateRequest request) {

        OrdemProducao op = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Ordem de produção não encontrada"));


        if (request.getQuantidadePlanejada() != null) {
            op.setQuantidadePlanejada(request.getQuantidadePlanejada());
        }

        if (request.getQuantidadeProduzida() != null) {
            op.setQuantidadeProduzida(request.getQuantidadeProduzida());
        }

        if (request.getOrigem() != null) {
            op.setOrigem(request.getOrigem().toUpperCase());
        }

        if (request.getObservacao() != null) {
            op.setObservacao(request.getObservacao());
        }

        if (request.getAtivo() != null) {
            op.setAtivo(request.getAtivo());
        }


        return mapper.toResponse(repository.save(op));
    }


    public void excluir(Long id) {

        OrdemProducao op = buscarEntidadePorId(id);

        op.setAtivo(false);

        repository.save(op);
    }

    @Transactional
    public OrdemProducaoResponse reservar(Long id) {

        OrdemProducao op = buscarEntidadePorId(id);

        if (op.getStatus() != StatusOrdemProducao.ABERTA) {
            throw new RegraNegocioException(
                    "Apenas OPs abertas podem ser reservadas.");
        }

        reservaEstoqueService.reservarMateriais(op);

        op.setStatus(StatusOrdemProducao.RESERVADA);

        return mapper.toResponse(repository.save(op));
    }

    @Transactional
    public OrdemProducaoResponse iniciar(Long id) {

        OrdemProducao op = buscarEntidadePorId(id);

        if (op.getStatus() != StatusOrdemProducao.RESERVADA) {
            throw new RegraNegocioException(
                    "Apenas OPs reservadas podem iniciar a produção.");
        }

        op.setStatus(StatusOrdemProducao.EM_PRODUCAO);

        return mapper.toResponse(
                repository.save(op)
        );
    }

    @Transactional
    public OrdemProducaoResponse concluir(Long id) {

        OrdemProducao op = buscarEntidadePorId(id);

        if (op.getStatus() != StatusOrdemProducao.EM_PRODUCAO) {
            throw new RegraNegocioException(
                    "Apenas OPs em produção podem ser concluídas.");
        }

        op.setStatus(StatusOrdemProducao.CONCLUIDA);

        return mapper.toResponse(
                repository.save(op)
        );
    }

    @Transactional
    public OrdemProducaoResponse cancelar(Long id) {

        OrdemProducao op = buscarEntidadePorId(id);

        if (op.getStatus() == StatusOrdemProducao.CONCLUIDA) {
            throw new RegraNegocioException(
                    "Não é possível cancelar uma OP concluída.");
        }

        op.setStatus(StatusOrdemProducao.CANCELADA);

        return mapper.toResponse(
                repository.save(op)
        );
    }

    @Transactional
    public OrdemProducaoResponse falhar(Long id) {

        OrdemProducao op = buscarEntidadePorId(id);

        if (op.getStatus() != StatusOrdemProducao.EM_PRODUCAO) {
            throw new RegraNegocioException(
                    "Apenas OPs em produção podem falhar.");
        }

        op.setStatus(StatusOrdemProducao.FALHA_PRODUCAO);

        return mapper.toResponse(
                repository.save(op)
        );
    }

    @Transactional
    public OrdemProducaoResponse reabrir(Long id) {

        OrdemProducao op = buscarEntidadePorId(id);

        if (op.getStatus() != StatusOrdemProducao.FALHA_PRODUCAO) {
            throw new RegraNegocioException(
                    "Apenas OPs com falha podem ser reabertas.");
        }

        op.setStatus(StatusOrdemProducao.RESERVADA);

        return mapper.toResponse(
                repository.save(op)
        );
    }

    private OrdemProducao buscarEntidadePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Ordem de produção não encontrada"));
    }
}