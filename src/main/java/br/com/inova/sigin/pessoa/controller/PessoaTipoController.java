package br.com.inova.sigin.pessoa.controller;

import br.com.inova.sigin.pessoa.dto.PessoaTipoRequest;
import br.com.inova.sigin.pessoa.service.PessoaTipoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
public class PessoaTipoController {

    private final PessoaTipoService service;


    @PostMapping("/{id}/tipos")
    public ResponseEntity<Void> adicionarTipo(
            @PathVariable Long id,
            @RequestBody @Valid PessoaTipoRequest request
    ) {

        service.adicionarTipo(id, request);

        return ResponseEntity.ok().build();
    }
}