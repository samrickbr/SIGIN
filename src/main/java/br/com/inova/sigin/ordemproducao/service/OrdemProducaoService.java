package br.com.inova.sigin.ordemproducao.service;

import br.com.inova.sigin.local.entity.Local;
import br.com.inova.sigin.local.repository.LocalRepository;
import br.com.inova.sigin.ordemproducao.dto.OrdemProducaoRequest;
import br.com.inova.sigin.ordemproducao.dto.OrdemProducaoResponse;
import br.com.inova.sigin.ordemproducao.dto.OrdemProducaoUpdateRequest;
import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import br.com.inova.sigin.ordemproducao.mapper.OrdemProducaoMapper;
import br.com.inova.sigin.ordemproducao.repository.OrdemProducaoRepository;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produto.repository.ProdutoRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .status("ABERTA")
                .origem(request.getOrigem().toUpperCase())
                .observacao(request.getObservacao())
                .quantidadeProduzida(java.math.BigDecimal.ZERO)
                .ativo(true)
                .dataAbertura(LocalDateTime.now())
                .build();


        return mapper.toResponse(repository.save(op));
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

        if (request.getStatus() != null) {
            op.setStatus(request.getStatus().toUpperCase());
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

        OrdemProducao op = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Ordem de produção não encontrada"));

        op.setAtivo(false);

        repository.save(op);
    }
}