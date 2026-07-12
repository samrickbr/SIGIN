package br.com.inova.sigin.apontamentoproducao.controller;

import br.com.inova.sigin.apontamentoproducao.dto.ApontamentoProducaoRequest;
import br.com.inova.sigin.apontamentoproducao.dto.ApontamentoProducaoResponse;
import br.com.inova.sigin.apontamentoproducao.dto.ApontamentoProducaoUpdateRequest;
import br.com.inova.sigin.apontamentoproducao.service.ApontamentoProducaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apontamentos-producao")
@RequiredArgsConstructor
public class ApontamentoProducaoController {

    private final ApontamentoProducaoService service;


    @PostMapping
    public ResponseEntity<ApontamentoProducaoResponse> criar(
            @RequestBody @Valid ApontamentoProducaoRequest request) {

        return ResponseEntity.ok(
                service.criar(request)
        );
    }


    @GetMapping("/ordem/{ordemProducaoId}")
    public ResponseEntity<List<ApontamentoProducaoResponse>> listarPorOp(
            @PathVariable Long ordemProducaoId) {

        return ResponseEntity.ok(
                service.listarPorOp(ordemProducaoId)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApontamentoProducaoResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApontamentoProducaoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody ApontamentoProducaoUpdateRequest request) {

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