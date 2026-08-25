package br.com.inova.sigin.produto.controller;

import br.com.inova.sigin.produto.dto.ProdutoRequest;
import br.com.inova.sigin.produto.dto.ProdutoResponse;
import br.com.inova.sigin.produto.enums.Setor;
import br.com.inova.sigin.produto.repository.ProdutoRepository;
import br.com.inova.sigin.produto.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final ProdutoService produtoService;

    @GetMapping
    public Page<ProdutoResponse> listar(
            @RequestParam(required = false) String busca,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(defaultValue = "false") boolean semCategoria,
            @RequestParam(required = false) Setor setor,
            @RequestParam(defaultValue = "false") boolean semSetor,
            @RequestParam(required = false) Boolean disponivelVenda,
            @RequestParam(required = false) Boolean ativo,
            @PageableDefault(size = 20, sort = "nome") Pageable pageable
    ) {
        return produtoService.listar(
                busca,
                categoriaId,
                semCategoria,
                setor,
                semSetor,
                disponivelVenda,
                ativo,
                pageable
        );
    }

    @PostMapping
    public ProdutoResponse salvar(@RequestBody @Valid ProdutoRequest request) {
        return produtoService.salvar(request);
    }

    @GetMapping("/{id}")
    public ProdutoResponse buscarPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ProdutoResponse atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ProdutoRequest request
    ) {
        return produtoService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        produtoService.excluir(id);
    }

    @GetMapping("/inativos")
    public List<ProdutoResponse> listarInativos() {
        return produtoService.listarInativos();
    }

    @GetMapping("/cardapio")
    public List<ProdutoResponse> cardapio() {
        return produtoService.listarDisponiveisVenda();
    }

}