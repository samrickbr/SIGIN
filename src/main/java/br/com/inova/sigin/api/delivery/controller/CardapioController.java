package br.com.inova.sigin.api.delivery.controller;

import br.com.inova.sigin.api.delivery.dto.CardapioResponse;
import br.com.inova.sigin.api.delivery.service.CardapioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery/cardapio")
@RequiredArgsConstructor
public class CardapioController {

    private final CardapioService service;

    @GetMapping
    public List<CardapioResponse> listar() {
        return service.listar();
    }

}