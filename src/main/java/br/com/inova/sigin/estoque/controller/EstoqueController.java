package br.com.inova.sigin.estoque.controller;

import br.com.inova.sigin.estoque.dto.EstoqueResponse;
import br.com.inova.sigin.estoque.service.EstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueService service;


    @GetMapping
    public ResponseEntity<List<EstoqueResponse>> listarSaldo() {

        return ResponseEntity.ok(
                service.listarSaldo()
        );
    }
}