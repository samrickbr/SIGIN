package br.com.inova.sigin.financeiro.controller;

import br.com.inova.sigin.financeiro.dto.ContaReceberResponse;
import br.com.inova.sigin.financeiro.service.ContaReceberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/financeiro/contas-receber")
@RequiredArgsConstructor
public class ContaReceberController {

    private final ContaReceberService service;

    @GetMapping
    public List<ContaReceberResponse> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public ContaReceberResponse buscar(
            @PathVariable Long id
    ){
        return service.buscar(id);
    }
}