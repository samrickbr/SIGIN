package br.com.inova.sigin.catalogo.controller;

import br.com.inova.sigin.catalogo.dto.CatalogoResponse;
import br.com.inova.sigin.catalogo.service.CatalogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo")
@RequiredArgsConstructor
public class CatalogoController {

    private final CatalogoService service;


    @GetMapping("/{canalVendaId}")
    public List<CatalogoResponse> listar(
            @PathVariable Long canalVendaId
    ) {

        return service.listar(canalVendaId);
    }
}