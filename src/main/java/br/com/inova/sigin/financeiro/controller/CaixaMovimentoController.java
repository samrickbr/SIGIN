package br.com.inova.sigin.financeiro.controller;

import br.com.inova.sigin.financeiro.dto.CaixaMovimentoRequest;
import br.com.inova.sigin.financeiro.dto.CaixaMovimentoResponse;
import br.com.inova.sigin.financeiro.service.CaixaMovimentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/financeiro/caixa")
@RequiredArgsConstructor
public class CaixaMovimentoController {

    private final CaixaMovimentoService service;

    @PostMapping
    public CaixaMovimentoResponse salvar(
            @RequestBody @Valid CaixaMovimentoRequest request
    ){
        return service.salvar(request);
    }

    @GetMapping
    public List<CaixaMovimentoResponse> listar(){
        return service.listar();
    }
}