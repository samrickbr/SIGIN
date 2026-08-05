package br.com.inova.sigin.usuario.controller;

import br.com.inova.sigin.usuario.dto.PermissaoRequest;
import br.com.inova.sigin.usuario.dto.PermissaoResponse;
import br.com.inova.sigin.usuario.service.PermissaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissoes")
@RequiredArgsConstructor
public class PermissaoController {

    private final PermissaoService service;


    @PostMapping
    public PermissaoResponse criar(
            @RequestBody @Valid PermissaoRequest request
    ){
        return service.criar(request);
    }


    @GetMapping
    public List<PermissaoResponse> listar(){

        return service.listar();
    }


    @GetMapping("/{id}")
    public PermissaoResponse buscar(
            @PathVariable Long id
    ){
        return service.buscar(id);
    }


    @PutMapping("/{id}")
    public PermissaoResponse atualizar(
            @PathVariable Long id,
            @RequestBody @Valid PermissaoRequest request
    ){
        return service.atualizar(id, request);
    }


    @DeleteMapping("/{id}")
    public void excluir(
            @PathVariable Long id
    ){
        service.excluir(id);
    }
}