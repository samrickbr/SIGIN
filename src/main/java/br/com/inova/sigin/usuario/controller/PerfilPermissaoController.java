package br.com.inova.sigin.usuario.controller;

import br.com.inova.sigin.usuario.dto.PerfilPermissaoRequest;
import br.com.inova.sigin.usuario.dto.PerfilPermissaoResponse;
import br.com.inova.sigin.usuario.service.PerfilPermissaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfil-permissoes")
@RequiredArgsConstructor
public class PerfilPermissaoController {

    private final PerfilPermissaoService service;


    @PostMapping
    public PerfilPermissaoResponse criar(
            @RequestBody @Valid PerfilPermissaoRequest request
    ){
        return service.criar(request);
    }


    @GetMapping
    public List<PerfilPermissaoResponse> listar(){

        return service.listar();
    }


    @DeleteMapping("/{id}")
    public void excluir(
            @PathVariable Long id
    ){
        service.excluir(id);
    }
}