package br.com.inova.sigin.opmaterial.controller;

import br.com.inova.sigin.opmaterial.dto.OpMaterialRequest;
import br.com.inova.sigin.opmaterial.dto.OpMaterialResponse;
import br.com.inova.sigin.opmaterial.dto.OpMaterialUpdateRequest;
import br.com.inova.sigin.opmaterial.service.OpMaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/op-materiais")
@RequiredArgsConstructor
public class OpMaterialController {

    private final OpMaterialService service;


    @PostMapping
    public ResponseEntity<OpMaterialResponse> criar(
            @RequestBody @Valid OpMaterialRequest request) {

        return ResponseEntity.ok(service.criar(request));
    }


    @GetMapping("/ordem/{ordemProducaoId}")
    public ResponseEntity<List<OpMaterialResponse>> listarPorOp(
            @PathVariable Long ordemProducaoId) {

        return ResponseEntity.ok(
                service.listarPorOp(ordemProducaoId)
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<OpMaterialResponse> atualizar(
            @PathVariable Long id,
            @RequestBody OpMaterialUpdateRequest request) {

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