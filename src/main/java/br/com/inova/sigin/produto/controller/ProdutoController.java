package br.com.inova.sigin.produto.controller;

import br.com.inova.sigin.produto.dto.ProdutoRequest;
import br.com.inova.sigin.produto.dto.ProdutoResponse;
import br.com.inova.sigin.produto.repository.ProdutoRepository;
import br.com.inova.sigin.produto.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final ProdutoService produtoService;


    @GetMapping
    public List<ProdutoResponse> listar() {
        return produtoService.listar();
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
}