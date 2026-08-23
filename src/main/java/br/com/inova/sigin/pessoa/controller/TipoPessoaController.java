package br.com.inova.sigin.pessoa.controller;

import br.com.inova.sigin.pessoa.dto.TipoPessoaRequest;
import br.com.inova.sigin.pessoa.dto.TipoPessoaResponse;
import br.com.inova.sigin.pessoa.service.PessoaAuthorizationService;
import br.com.inova.sigin.pessoa.service.TipoPessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-pessoa")
@RequiredArgsConstructor
public class TipoPessoaController {

    private final TipoPessoaService service;
    private final PessoaAuthorizationService authorizationService;

    @PostMapping
    public ResponseEntity<TipoPessoaResponse> criar(
            @RequestBody @Valid TipoPessoaRequest request,
            Authentication authentication
    ) {
        authorizationService.verificarAcessoAdministrativo(authentication);

        return ResponseEntity.ok(service.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<TipoPessoaResponse>> listar(
            Authentication authentication
    ) {
        authorizationService.verificarAcessoAdministrativo(authentication);

        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoPessoaResponse> buscarPorId(
            @PathVariable Long id,
            Authentication authentication
    ) {
        authorizationService.verificarAcessoAdministrativo(authentication);

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoPessoaResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid TipoPessoaRequest request,
            Authentication authentication
    ) {
        authorizationService.verificarAcessoAdministrativo(authentication);

        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @PatchMapping("/{id}/ativo")
    public ResponseEntity<TipoPessoaResponse> alterarAtivo(
            @PathVariable Long id,
            @RequestParam boolean ativo,
            Authentication authentication
    ) {
        authorizationService.verificarAcessoAdministrativo(authentication);

        return ResponseEntity.ok(service.alterarAtivo(id, ativo));
    }
}