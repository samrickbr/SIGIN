package br.com.inova.sigin.local.controller;

import br.com.inova.sigin.local.dto.LocalRequest;
import br.com.inova.sigin.local.dto.LocalResponse;
import br.com.inova.sigin.local.dto.LocalUpdateRequest;
import br.com.inova.sigin.local.service.LocalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locais")
@RequiredArgsConstructor
public class LocalController {

    private final LocalService service;

    @PostMapping
    public LocalResponse salvar(@RequestBody @Valid LocalRequest request) {
        return service.salvar(request);
    }

    @GetMapping
    public List<LocalResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public LocalResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public LocalResponse atualizar(
            @PathVariable Long id,
            @RequestBody @Valid LocalUpdateRequest request) {

        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}