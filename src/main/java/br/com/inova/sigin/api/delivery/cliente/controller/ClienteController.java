package br.com.inova.sigin.api.delivery.cliente.controller;

import br.com.inova.sigin.api.delivery.cliente.dto.ClienteRequest;
import br.com.inova.sigin.api.delivery.cliente.dto.ClienteResponse;
import br.com.inova.sigin.api.delivery.cliente.service.ClienteDeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import br.com.inova.sigin.pessoa.dto.PessoaEnderecoRequest;
import br.com.inova.sigin.pessoa.dto.PessoaEnderecoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/delivery/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteDeliveryService service;
    @PostMapping
    public ClienteResponse criar(
            @RequestBody @Valid ClienteRequest request
    ) {
        return service.criar(request);
    }
    @GetMapping("/telefone/{telefone}")
    public ClienteResponse buscar(
            @PathVariable String telefone
    ) {
        return service.buscarPorTelefone(telefone);
    }
    @GetMapping("/documento/{documento}")
    public ClienteResponse buscarPorDocumento(
            @PathVariable String documento
    ) {
        return service.buscarPorDocumento(documento);
    }
    @GetMapping("/meus-enderecos")
    public ResponseEntity<List<PessoaEnderecoResponse>> listarEnderecos(
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                service.listarEnderecos(authentication)
        );
    }

    @PostMapping("/meus-enderecos")
    public ResponseEntity<PessoaEnderecoResponse> criarEndereco(
            Authentication authentication,
            @RequestBody @Valid PessoaEnderecoRequest request
    ) {
        return ResponseEntity.ok(
                service.criarEndereco(
                        authentication,
                        request
                )
        );
    }

    @GetMapping("/meus-enderecos/{enderecoId}")
    public ResponseEntity<PessoaEnderecoResponse> buscarEndereco(
            Authentication authentication,
            @PathVariable Long enderecoId
    ) {
        return ResponseEntity.ok(
                service.buscarEndereco(
                        authentication,
                        enderecoId
                )
        );
    }

    @PutMapping("/meus-enderecos/{enderecoId}")
    public ResponseEntity<PessoaEnderecoResponse> atualizarEndereco(
            Authentication authentication,
            @PathVariable Long enderecoId,
            @RequestBody @Valid PessoaEnderecoRequest request
    ) {
        return ResponseEntity.ok(
                service.atualizarEndereco(
                        authentication,
                        enderecoId,
                        request
                )
        );
    }

    @PutMapping("/meus-enderecos/{enderecoId}/principal")
    public ResponseEntity<PessoaEnderecoResponse> definirEnderecoPrincipal(
            Authentication authentication,
            @PathVariable Long enderecoId
    ) {
        return ResponseEntity.ok(
                service.definirEnderecoPrincipal(
                        authentication,
                        enderecoId
                )
        );
    }
    @DeleteMapping("/meus-enderecos/{enderecoId}")
    public ResponseEntity<Void> excluirEndereco(
            Authentication authentication,
            @PathVariable Long enderecoId
    ) {
        service.excluirEndereco(
                authentication,
                enderecoId
        );

        return ResponseEntity.noContent().build();
    }
}