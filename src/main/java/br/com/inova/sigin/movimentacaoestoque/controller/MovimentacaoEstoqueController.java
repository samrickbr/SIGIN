package br.com.inova.sigin.movimentacaoestoque.controller;

import br.com.inova.sigin.movimentacaoestoque.dto.MovimentacaoEstoqueRequest;
import br.com.inova.sigin.movimentacaoestoque.dto.MovimentacaoEstoqueResponse;
import br.com.inova.sigin.movimentacaoestoque.dto.MovimentacaoEstoqueUpdateRequest;
import br.com.inova.sigin.movimentacaoestoque.service.MovimentacaoEstoqueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimentacoes-estoque")
@RequiredArgsConstructor
public class MovimentacaoEstoqueController {

    private final MovimentacaoEstoqueService service;


    @PostMapping
    public ResponseEntity<MovimentacaoEstoqueResponse> criar(
            @RequestBody @Valid MovimentacaoEstoqueRequest request) {

        return ResponseEntity.ok(
                service.criar(request)
        );
    }


    @GetMapping
    public ResponseEntity<List<MovimentacaoEstoqueResponse>> listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<MovimentacaoEstoqueResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<MovimentacaoEstoqueResponse> atualizar(
            @PathVariable Long id,
            @RequestBody MovimentacaoEstoqueUpdateRequest request) {

        return ResponseEntity.ok(
                service.atualizar(id, request)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity.noContent().build();
    }
}