package br.com.inova.sigin.produto.service;

import br.com.inova.sigin.produto.dto.ProdutoRequest;
import br.com.inova.sigin.produto.dto.ProdutoResponse;
import br.com.inova.sigin.produto.entity.Categoria;
import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produto.repository.CategoriaRepository;
import br.com.inova.sigin.produto.repository.ProdutoRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import br.com.inova.sigin.shared.service.GeradorCodigoService;
import lombok.RequiredArgsConstructor;
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
                .categoria(categoria)
                .build();


        Produto salvo = produtoRepository.save(produto);

        salvo.setCodigo(
                geradorCodigoService.gerarCodigoProduto(salvo.getId())
        );

        salvo = produtoRepository.save(salvo);


        return ProdutoResponse.builder()
                .id(salvo.getId())
                .codigo(salvo.getCodigo())
                .nome(salvo.getNome())
                .descricao(salvo.getDescricao())
                .categoria(
                        salvo.getCategoria() != null
                                ? salvo.getCategoria().getNome()
                                : null
                )
                .ativo(salvo.getAtivo())
                .build();
    }
    @Transactional(readOnly = true)
    public ProdutoResponse buscarPorId(Long id) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Produto não encontrado")
                );

        return ProdutoResponse.builder()
                .id(produto.getId())
                .codigo(produto.getCodigo())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .categoria(
                        produto.getCategoria() != null
                                ? produto.getCategoria().getNome()
                                : null
                )
                .ativo(produto.getAtivo())
                .build();
    }

    @Transactional
    public ProdutoResponse atualizar(Long id, ProdutoRequest request) {

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Produto não encontrado")
                );

        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());

        if (request.getCategoriaId() != null) {

            Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                    .orElseThrow(() ->
                            new RegraNegocioException("Categoria não encontrada")
                    );

            produto.setCategoria(categoria);
        }

        Produto atualizado = produtoRepository.save(produto);

        return ProdutoResponse.builder()
                .id(atualizado.getId())
                .codigo(atualizado.getCodigo())
                .nome(atualizado.getNome())
                .descricao(atualizado.getDescricao())
                .categoria(
                        atualizado.getCategoria() != null
                                ? atualizado.getCategoria().getNome()
                                : null
                )
                .ativo(atualizado.getAtivo())
                .build();
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
    public List<ProdutoResponse> listar() {

        return produtoRepository.findByAtivoTrue()
                .stream()
                .map(produto -> ProdutoResponse.builder()
                        .id(produto.getId())
                        .codigo(produto.getCodigo())
                        .nome(produto.getNome())
                        .descricao(produto.getDescricao())
                        .categoria(
                                produto.getCategoria() != null
                                        ? produto.getCategoria().getNome()
                                        : null
                        )
                        .ativo(produto.getAtivo())
                        .build()
                )
                .toList();
    }
    @Transactional(readOnly = true)
    public List<ProdutoResponse> listarInativos() {

        return produtoRepository.findByAtivoFalse()
                .stream()
                .map(produto -> ProdutoResponse.builder()
                        .id(produto.getId())
                        .codigo(produto.getCodigo())
                        .nome(produto.getNome())
                        .descricao(produto.getDescricao())
                        .categoria(
                                produto.getCategoria() != null
                                        ? produto.getCategoria().getNome()
                                        : null
                        )
                        .ativo(produto.getAtivo())
                        .build()
                )
                .toList();
    }
}