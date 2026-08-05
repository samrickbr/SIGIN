package br.com.inova.sigin.usuario.controller;

import br.com.inova.sigin.usuario.dto.UsuarioRequest;
import br.com.inova.sigin.usuario.dto.UsuarioResponse;
import br.com.inova.sigin.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public UsuarioResponse criar(@Valid @RequestBody UsuarioRequest request) {
        return service.criar(request);
    }

    @GetMapping
    public List<UsuarioResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public UsuarioResponse atualizar(@PathVariable Long id,
                                     @Valid @RequestBody UsuarioRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}