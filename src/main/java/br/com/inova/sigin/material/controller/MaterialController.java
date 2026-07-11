package br.com.inova.sigin.material.controller;

import br.com.inova.sigin.material.dto.MaterialRequest;
import br.com.inova.sigin.material.dto.MaterialResponse;
import br.com.inova.sigin.material.dto.MaterialUpdateRequest;
import br.com.inova.sigin.material.service.MaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materiais")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService service;


    @PostMapping
    public ResponseEntity<MaterialResponse> criar(
            @RequestBody @Valid MaterialRequest request
    ) {

        return ResponseEntity.ok(service.criar(request));
    }


    @GetMapping
    public ResponseEntity<List<MaterialResponse>> listar() {

        return ResponseEntity.ok(service.listar());
    }


    @GetMapping("/{id}")
    public ResponseEntity<MaterialResponse> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<MaterialResponse> atualizar(
            @PathVariable Long id,
            @RequestBody MaterialUpdateRequest request
    ) {

        return ResponseEntity.ok(service.atualizar(id, request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id
    ) {

        service.excluir(id);

        return ResponseEntity.noContent().build();
    }
}