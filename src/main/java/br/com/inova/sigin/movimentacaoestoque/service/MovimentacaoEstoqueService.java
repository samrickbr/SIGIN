package br.com.inova.sigin.movimentacaoestoque.service;

import br.com.inova.sigin.local.entity.Local;
import br.com.inova.sigin.local.repository.LocalRepository;
import br.com.inova.sigin.material.entity.Material;
import br.com.inova.sigin.material.repository.MaterialRepository;
import br.com.inova.sigin.movimentacaoestoque.dto.MovimentacaoEstoqueRequest;
import br.com.inova.sigin.movimentacaoestoque.dto.MovimentacaoEstoqueResponse;
import br.com.inova.sigin.movimentacaoestoque.dto.MovimentacaoEstoqueUpdateRequest;
import br.com.inova.sigin.movimentacaoestoque.entity.MovimentacaoEstoque;
import br.com.inova.sigin.movimentacaoestoque.mapper.MovimentacaoEstoqueMapper;
import br.com.inova.sigin.movimentacaoestoque.repository.MovimentacaoEstoqueRepository;
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
public class MovimentacaoEstoqueService {

    private final MovimentacaoEstoqueRepository repository;
    private final ProdutoRepository produtoRepository;
    private final MaterialRepository materialRepository;
    private final LocalRepository localRepository;
    private final PessoaRepository pessoaRepository;
    private final MovimentacaoEstoqueMapper mapper;


    public MovimentacaoEstoqueResponse criar(
            MovimentacaoEstoqueRequest request) {

        validarProdutoMaterial(request);
        Produto produto = null;
        Material material = null;
        Pessoa responsavel = null;

        if (request.getProdutoId() != null) {
            produto = produtoRepository.findById(request.getProdutoId())
                    .orElseThrow(() ->
                            new RegraNegocioException(
                                    "Produto não encontrado"
                            ));
        }

        if (request.getMaterialId() != null) {
            material = materialRepository.findById(request.getMaterialId())
                    .orElseThrow(() ->
                            new RegraNegocioException(
                                    "Material não encontrado"
                            ));
        }

        Local local = localRepository.findById(request.getLocalId())
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Local não encontrado"
                        ));

        if (request.getResponsavelId() != null) {
            responsavel = pessoaRepository.findById(request.getResponsavelId())
                    .orElseThrow(() ->
                            new RegraNegocioException(
                                    "Responsável não encontrado"
                            ));
        }

        MovimentacaoEstoque entity = MovimentacaoEstoque.builder()
                .produto(produto)
                .material(material)
                .local(local)
                .tipo(request.getTipo().toUpperCase())
                .movimento(
                        request.getMovimento() != null
                                ? request.getMovimento().toUpperCase()
                                : definirMovimento(request.getTipo())
                )
                .quantidade(request.getQuantidade())
                .origem(request.getOrigem().toUpperCase())
                .referenciaId(request.getReferenciaId())
                .observacao(request.getObservacao())
                .responsavel(responsavel)
                .dataMovimentacao(LocalDateTime.now())
                .ativo(true)
                .build();

        return mapper.toResponse(repository.save(entity));
    }


    private void validarProdutoMaterial(
            MovimentacaoEstoqueRequest request) {

        boolean possuiProduto = request.getProdutoId() != null;
        boolean possuiMaterial = request.getMaterialId() != null;


        if (possuiProduto == possuiMaterial) {

            throw new RegraNegocioException(
                    "Informe produto ou material, somente um deles."
            );
        }
    }


    public List<MovimentacaoEstoqueResponse> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }


    public MovimentacaoEstoqueResponse buscarPorId(Long id) {

        MovimentacaoEstoque entity = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Movimentação não encontrada"
                        ));

        return mapper.toResponse(entity);
    }

    public MovimentacaoEstoqueResponse atualizar(
            Long id,
            MovimentacaoEstoqueUpdateRequest request) {

        MovimentacaoEstoque entity = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Movimentação não encontrada"
                        ));


        if (request.getQuantidade() != null) {
            entity.setQuantidade(request.getQuantidade());
        }

        if (request.getObservacao() != null) {
            entity.setObservacao(request.getObservacao());
        }

        if (request.getAtivo() != null) {
            entity.setAtivo(request.getAtivo());
        }
        return mapper.toResponse(repository.save(entity));
    }

    public void excluir(Long id) {

        MovimentacaoEstoque entity = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Movimentação não encontrada"
                        ));

        entity.setAtivo(false);

        repository.save(entity);
    }

    private String definirMovimento(String tipo) {

        return switch (tipo.toUpperCase()) {

            case "COMPRA",
                    "PRODUCAO",
                    "AJUSTE_ENTRADA" -> "ENTRADA";

            case "CONSUMO_PRODUCAO",
                    "PERDA",
                    "VENDA",
                    "AJUSTE_SAIDA" -> "SAIDA";

            default -> throw new RegraNegocioException(
                    "Tipo de movimentação inválido"
            );
        };
    }
}