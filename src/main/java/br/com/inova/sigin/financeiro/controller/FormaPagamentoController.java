package br.com.inova.sigin.financeiro.controller;

import br.com.inova.sigin.financeiro.dto.FormaPagamentoRequest;
import br.com.inova.sigin.financeiro.dto.FormaPagamentoResponse;
import br.com.inova.sigin.financeiro.service.FormaPagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/financeiro/formas-pagamento")
@RequiredArgsConstructor
public class FormaPagamentoController {

    private final FormaPagamentoService service;

    @PostMapping
    public FormaPagamentoResponse salvar(
            @RequestBody @Valid FormaPagamentoRequest dto
    ) {
        return service.salvar(dto);
    }

    @GetMapping
    public List<FormaPagamentoResponse> listar() {
        return service.listar();
    }

    @PutMapping("/{id}")
    public FormaPagamentoResponse atualizar(
            @PathVariable Long id,
            @RequestBody @Valid FormaPagamentoRequest dto
    ) {
        return service.atualizar(id, dto);
    }

    @PatchMapping("/{id}/ativo")
    public FormaPagamentoResponse alterarAtivo(
            @PathVariable Long id,
            @RequestBody Boolean ativo
    ) {
        return service.alterarAtivo(id, ativo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id
    ) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}