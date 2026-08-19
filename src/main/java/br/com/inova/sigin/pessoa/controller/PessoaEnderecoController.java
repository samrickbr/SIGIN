package br.com.inova.sigin.pessoa.controller;

import br.com.inova.sigin.pessoa.dto.PessoaEnderecoRequest;
import br.com.inova.sigin.pessoa.dto.PessoaEnderecoResponse;
import br.com.inova.sigin.pessoa.service.PessoaEnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas/{pessoaId}/enderecos")
@RequiredArgsConstructor
public class PessoaEnderecoController {

    private final PessoaEnderecoService service;

    @PostMapping
    public ResponseEntity<PessoaEnderecoResponse> criar(
            @PathVariable Long pessoaId,
            @RequestBody @Valid PessoaEnderecoRequest request
    ) {

        return ResponseEntity.ok(
                service.criar(pessoaId, request)
        );
    }

    @GetMapping
    public ResponseEntity<List<PessoaEnderecoResponse>> listar(
            @PathVariable Long pessoaId
    ) {

        return ResponseEntity.ok(
                service.listar(pessoaId)
        );
    }

    @GetMapping("/{enderecoId}")
    public ResponseEntity<PessoaEnderecoResponse> buscarPorId(
            @PathVariable Long pessoaId,
            @PathVariable Long enderecoId
    ) {

        return ResponseEntity.ok(
                service.buscarPorId(pessoaId, enderecoId)
        );
    }

    @PutMapping("/{enderecoId}")
    public ResponseEntity<PessoaEnderecoResponse> atualizar(
            @PathVariable Long pessoaId,
            @PathVariable Long enderecoId,
            @RequestBody @Valid PessoaEnderecoRequest request
    ) {

        return ResponseEntity.ok(
                service.atualizar(
                        pessoaId,
                        enderecoId,
                        request
                )
        );
    }

    @PutMapping("/{enderecoId}/principal")
    public ResponseEntity<PessoaEnderecoResponse> definirPrincipal(
            @PathVariable Long pessoaId,
            @PathVariable Long enderecoId
    ) {

        return ResponseEntity.ok(
                service.definirPrincipal(
                        pessoaId,
                        enderecoId
                )
        );
    }
}