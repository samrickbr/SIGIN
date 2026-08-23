package br.com.inova.sigin.pessoa.controller;

import br.com.inova.sigin.pessoa.dto.PessoaRequest;
import br.com.inova.sigin.pessoa.dto.PessoaResponse;
import br.com.inova.sigin.pessoa.dto.PessoaUpdateRequest;
import br.com.inova.sigin.pessoa.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService service;

    @PostMapping
    public ResponseEntity<PessoaResponse> criar(
            @RequestBody @Valid PessoaRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                service.criar(request, authentication)
        );
    }

    @GetMapping
    public ResponseEntity<List<PessoaResponse>> listar(
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                service.listar(authentication)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponse> buscarPorId(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                service.buscarPorId(id, authentication)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponse> atualizar(
            @PathVariable Long id,
            @RequestBody PessoaUpdateRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                service.atualizar(
                        id,
                        request,
                        authentication
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id,
            Authentication authentication
    ) {
        service.excluir(
                id,
                authentication
        );

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/por-documento")
    public ResponseEntity<PessoaResponse> buscarPorDocumento(
            @RequestParam String documento,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                service.buscarPorDocumento(
                        documento,
                        authentication
                )
        );
    }

    @GetMapping("/por-telefone")
    public ResponseEntity<PessoaResponse> buscarPorTelefone(
            @RequestParam String telefone,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                service.buscarPorTelefone(
                        telefone,
                        authentication
                )
        );
    }
}