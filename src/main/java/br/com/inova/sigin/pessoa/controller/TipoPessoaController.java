package br.com.inova.sigin.pessoa.controller;

import br.com.inova.sigin.pessoa.dto.TipoPessoaRequest;
import br.com.inova.sigin.pessoa.dto.TipoPessoaResponse;
import br.com.inova.sigin.pessoa.service.TipoPessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-pessoa")
@RequiredArgsConstructor
public class TipoPessoaController {

    private final TipoPessoaService service;


    @PostMapping
    public ResponseEntity<TipoPessoaResponse> criar(
            @RequestBody @Valid TipoPessoaRequest request
    ) {

        return ResponseEntity.ok(service.criar(request));
    }


    @GetMapping
    public ResponseEntity<List<TipoPessoaResponse>> listar() {

        return ResponseEntity.ok(service.listar());
    }
}