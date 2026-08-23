package br.com.inova.sigin.pessoa.controller;

import br.com.inova.sigin.pessoa.dto.CepResponse;
import br.com.inova.sigin.pessoa.service.CepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ceps")
@RequiredArgsConstructor
public class CepController {

    private final CepService service;

    @GetMapping("/{cep}")
    public ResponseEntity<CepResponse> buscar(
            @PathVariable String cep
    ) {
        return ResponseEntity.ok(
                service.buscar(cep)
        );
    }
}