package br.com.inova.sigin.produtomaterial.controller;

import br.com.inova.sigin.produtomaterial.dto.ProdutoMaterialRequest;
import br.com.inova.sigin.produtomaterial.dto.ProdutoMaterialResponse;
import br.com.inova.sigin.produtomaterial.dto.ProdutoMaterialUpdateRequest;
import br.com.inova.sigin.produtomaterial.service.ProdutoMaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto-materiais")
@RequiredArgsConstructor
public class ProdutoMaterialController {

    private final ProdutoMaterialService service;

    @PostMapping
    public ResponseEntity<ProdutoMaterialResponse> criar(
            @RequestBody @Valid ProdutoMaterialRequest request) {

        return ResponseEntity.ok(service.criar(request));
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<ProdutoMaterialResponse>> listarPorProduto(
            @PathVariable Long produtoId) {

        return ResponseEntity.ok(service.listarPorProduto(produtoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoMaterialResponse> atualizar(
            @PathVariable Long id,
            @RequestBody ProdutoMaterialUpdateRequest request) {

        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity.noContent().build();
    }
}