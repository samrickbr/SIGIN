package br.com.inova.sigin.pessoa.controller;

import br.com.inova.sigin.pessoa.dto.PessoaEnderecoResponse;
import br.com.inova.sigin.pessoa.dto.PessoaRequest;
import br.com.inova.sigin.pessoa.dto.PessoaResponse;
import br.com.inova.sigin.pessoa.dto.PessoaUpdateRequest;
import br.com.inova.sigin.pessoa.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService service;

    @PostMapping
    public ResponseEntity<PessoaResponse> criar(
            @RequestBody @Valid PessoaRequest request
    ) {

        return ResponseEntity.ok(service.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<PessoaResponse>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponse> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponse> atualizar(
            @PathVariable Long id,
            @RequestBody PessoaUpdateRequest request
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

    @GetMapping("/por-documento")
    public ResponseEntity<PessoaResponse> buscarPorDocumento(
            @RequestParam String documento
    ) {
        return ResponseEntity.ok(
                service.buscarPorDocumento(documento)
        );
    }

    @GetMapping("/por-telefone")
    public ResponseEntity<PessoaResponse> buscarPorTelefone(
            @RequestParam String telefone
    ) {
        return ResponseEntity.ok(
                service.buscarPorTelefone(telefone)
        );
    }

    @GetMapping("/{id}/enderecos")
    public ResponseEntity<List<PessoaEnderecoResponse>> listarEnderecos(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                service.listarEnderecos(id)
        );
    }
}