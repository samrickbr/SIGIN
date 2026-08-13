package br.com.inova.sigin.usuario.controller;

import br.com.inova.sigin.usuario.dto.PerfilRequest;
import br.com.inova.sigin.usuario.dto.PerfilResponse;
import br.com.inova.sigin.usuario.service.PerfilService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfis")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService service;

    @PostMapping
    public PerfilResponse criar(@Valid @RequestBody PerfilRequest request) {
        return service.criar(request);
    }

    @GetMapping
    public List<PerfilResponse> listar(
            @RequestParam(required = false) Boolean ativo
    ) {
        return service.listar(ativo);
    }

    @GetMapping("/{id}")
    public PerfilResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public PerfilResponse atualizar(@PathVariable Long id,
                                    @Valid @RequestBody PerfilRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}