package br.com.inova.sigin.pessoa.controller;

import br.com.inova.sigin.pessoa.dto.PessoaEnderecoRequest;
import br.com.inova.sigin.pessoa.dto.PessoaEnderecoResponse;
import br.com.inova.sigin.pessoa.service.PessoaEnderecoService;
import br.com.inova.sigin.pessoa.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas/{pessoaId}/enderecos")
@RequiredArgsConstructor
public class PessoaEnderecoController {

    private final PessoaEnderecoService service;
    private final PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<PessoaEnderecoResponse> criar(
            @PathVariable Long pessoaId,
            @RequestBody @Valid PessoaEnderecoRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                service.criar(
                        pessoaId,
                        request,
                        authentication
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<PessoaEnderecoResponse>> listar(
            @PathVariable Long pessoaId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                pessoaService.listarEnderecos(
                        pessoaId,
                        authentication
                )
        );
    }

    @GetMapping("/{enderecoId}")
    public ResponseEntity<PessoaEnderecoResponse> buscarPorId(
            @PathVariable Long pessoaId,
            @PathVariable Long enderecoId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                service.buscarPorId(
                        pessoaId,
                        enderecoId,
                        authentication
                )
        );
    }

    @PutMapping("/{enderecoId}")
    public ResponseEntity<PessoaEnderecoResponse> atualizar(
            @PathVariable Long pessoaId,
            @PathVariable Long enderecoId,
            @RequestBody @Valid PessoaEnderecoRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                service.atualizar(
                        pessoaId,
                        enderecoId,
                        request,
                        authentication
                )
        );
    }

    @PutMapping("/{enderecoId}/principal")
    public ResponseEntity<PessoaEnderecoResponse> definirPrincipal(
            @PathVariable Long pessoaId,
            @PathVariable Long enderecoId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                service.definirPrincipal(
                        pessoaId,
                        enderecoId,
                        authentication
                )
        );
    }

    @DeleteMapping("/{enderecoId}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long pessoaId,
            @PathVariable Long enderecoId,
            Authentication authentication
    ) {
        service.excluir(
                pessoaId,
                enderecoId,
                authentication
        );

        return ResponseEntity.noContent().build();
    }
}