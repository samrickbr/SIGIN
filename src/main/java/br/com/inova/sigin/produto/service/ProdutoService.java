package br.com.inova.sigin.produto.service;

import br.com.inova.sigin.produto.dto.ProdutoRequest;
import br.com.inova.sigin.produto.dto.ProdutoResponse;
import br.com.inova.sigin.produto.entity.Categoria;
import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produto.enums.Setor;
import br.com.inova.sigin.produto.repository.CategoriaRepository;
import br.com.inova.sigin.produto.repository.ProdutoRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import br.com.inova.sigin.shared.service.GeradorCodigoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final GeradorCodigoService geradorCodigoService;

    @Transactional
    public ProdutoResponse salvar(ProdutoRequest request) {

        if (request.getSetor() == null) {
            throw new RegraNegocioException(
                    "Setor é obrigatório para criação do produto."
            );
        }

        Categoria categoria = null;

        if (request.getCategoriaId() != null) {
            categoria = categoriaRepository.findById(request.getCategoriaId())
                    .orElseThrow(() ->
                            new RegraNegocioException("Categoria não encontrada")
                    );
        }

        Produto produto = Produto.builder()
                .nome(request.getNome())
                .descricao(request.getDescricao())
                .precoVenda(request.getPrecoVenda())
                .disponivelVenda(request.getDisponivelVenda())
                .imagem(request.getImagem())
                .categoria(categoria)
                .setor(request.getSetor())
                .build();

        Produto salvo = produtoRepository.save(produto);

        salvo.setCodigo(
                geradorCodigoService.gerarCodigoProduto(salvo.getId())
        );

        salvo = produtoRepository.save(salvo);

        return toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public ProdutoResponse buscarPorId(Long id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Produto não encontrado")
                );

        return toResponse(produto);
    }

    @Transactional
    public ProdutoResponse atualizar(Long id, ProdutoRequest request) {

        if (request.getSetor() == null) {
            throw new RegraNegocioException(
                    "Setor é obrigatório para atualização do produto."
            );
        }

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Produto não encontrado")
                );

        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());
        produto.setPrecoVenda(request.getPrecoVenda());
        produto.setDisponivelVenda(request.getDisponivelVenda());
        produto.setImagem(request.getImagem());
        produto.setSetor(request.getSetor());

        if (request.getCategoriaId() != null) {

            Categoria categoria = categoriaRepository.findById(
                            request.getCategoriaId()
                    )
                    .orElseThrow(() ->
                            new RegraNegocioException(
                                    "Categoria não encontrada"
                            )
                    );

            produto.setCategoria(categoria);
        } else {
            produto.setCategoria(null);
        }

        Produto atualizado = produtoRepository.save(produto);

        return toResponse(atualizado);
    }

    @Transactional
    public void excluir(Long id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Produto não encontrado")
                );

        produto.setAtivo(false);

        produtoRepository.save(produto);
    }

    @Transactional(readOnly = true)
    public Page<ProdutoResponse> listar(
            String busca,
            Long categoriaId,
            boolean semCategoria,
            Setor setor,
            boolean semSetor,
            Boolean disponivelVenda,
            Boolean ativo,
            Pageable pageable
    ) {
        Specification<Produto> specification = (root, query, cb) -> cb.conjunction();

        if (busca != null && !busca.isBlank()) {
            String termo = "%" + busca.trim().toLowerCase() + "%";

            specification = specification.and((root, query, cb) ->
                    cb.or(
                            cb.like(cb.lower(root.get("codigo")), termo),
                            cb.like(cb.lower(root.get("nome")), termo),
                            cb.like(cb.lower(root.get("descricao")), termo)
                    )
            );
        }

        if (semCategoria) {
            specification = specification.and((root, query, cb) ->
                    cb.isNull(root.get("categoria"))
            );
        } else if (categoriaId != null) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("categoria").get("id"), categoriaId)
            );
        }

        if (semSetor) {
            specification = specification.and((root, query, cb) ->
                    cb.isNull(root.get("setor"))
            );
        } else if (setor != null) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("setor"), setor)
            );
        }

        if (disponivelVenda != null) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("disponivelVenda"), disponivelVenda)
            );
        }

        if (ativo != null) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("ativo"), ativo)
            );
        }

        return produtoRepository
                .findAll(specification, pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponse> listarInativos() {

        return produtoRepository.findByAtivoFalse()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponse> listarDisponiveisVenda() {

        return produtoRepository
                .findProdutosDisponiveisVenda()
                .stream()
                .map(produto -> ProdutoResponse.builder()
                        .id(produto.getId())
                        .nome(produto.getNome())
                        .descricao(produto.getDescricao())
                        .ativo(produto.getAtivo())
                        .setor(produto.getSetor())
                        .build()
                )
                .toList();
    }

    private ProdutoResponse toResponse(Produto produto) {

        return ProdutoResponse.builder()
                .id(produto.getId())
                .codigo(produto.getCodigo())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .precoVenda(produto.getPrecoVenda())
                .disponivelVenda(produto.getDisponivelVenda())
                .imagem(produto.getImagem())
                .categoriaId(
                        produto.getCategoria() != null
                                ? produto.getCategoria().getId()
                                : null
                )
                .categoria(
                        produto.getCategoria() != null
                                ? produto.getCategoria().getNome()
                                : null
                )
                .ativo(produto.getAtivo())
                .setor(produto.getSetor())
                .build();
    }
}