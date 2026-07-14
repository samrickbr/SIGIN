package br.com.inova.sigin.ordemproducao.controller;

import br.com.inova.sigin.ordemproducao.dto.OrdemProducaoRequest;
import br.com.inova.sigin.ordemproducao.dto.OrdemProducaoResponse;
import br.com.inova.sigin.ordemproducao.dto.OrdemProducaoUpdateRequest;
import br.com.inova.sigin.ordemproducao.service.OrdemProducaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordens-producao")
@RequiredArgsConstructor
public class OrdemProducaoController {

    private final OrdemProducaoService service;


    @PostMapping
    public ResponseEntity<OrdemProducaoResponse> criar(
            @RequestBody @Valid OrdemProducaoRequest request) {

        return ResponseEntity.ok(service.criar(request));
    }


    @GetMapping
    public ResponseEntity<List<OrdemProducaoResponse>> listar() {

        return ResponseEntity.ok(service.listar());
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrdemProducaoResponse> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<OrdemProducaoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody OrdemProducaoUpdateRequest request) {

        return ResponseEntity.ok(service.atualizar(id, request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        service.excluir(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/reservar")
    public ResponseEntity<OrdemProducaoResponse> reservar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.reservar(id)
        );
    }

    @PostMapping("/{id}/iniciar")
    public ResponseEntity<OrdemProducaoResponse> iniciar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.iniciar(id)
        );
    }

    @PostMapping("/{id}/concluir")
    public ResponseEntity<OrdemProducaoResponse> concluir(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.concluir(id)
        );
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<OrdemProducaoResponse> cancelar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.cancelar(id)
        );
    }

    @PostMapping("/{id}/falhar")
    public ResponseEntity<OrdemProducaoResponse> falhar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.falhar(id)
        );
    }

    @PostMapping("/{id}/reabrir")
    public ResponseEntity<OrdemProducaoResponse> reabrir(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.reabrir(id)
        );
    }
}