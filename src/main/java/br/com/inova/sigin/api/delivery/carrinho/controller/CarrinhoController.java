package br.com.inova.sigin.api.delivery.carrinho.controller;

import br.com.inova.sigin.api.delivery.carrinho.dto.AdicionarItemRequest;
import br.com.inova.sigin.api.delivery.carrinho.dto.CarrinhoResponse;
import br.com.inova.sigin.api.delivery.carrinho.dto.CriarCarrinhoRequest;
import br.com.inova.sigin.api.delivery.carrinho.service.CarrinhoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery/carrinho")
@RequiredArgsConstructor
public class CarrinhoController {

    private final CarrinhoService service;

    @PostMapping
    public CarrinhoResponse criar(
            @RequestBody @Valid CriarCarrinhoRequest request
    ) {
        return service.criar(request.getClienteId());
    }

    @GetMapping("/{id}")
    public CarrinhoResponse buscar(
            @PathVariable Long id
    ) {
        return service.buscar(id);
    }
    @PostMapping("/{id}/itens")
    public CarrinhoResponse adicionarItem(
            @PathVariable Long id,
            @RequestBody @Valid AdicionarItemRequest request
    ) {
        return service.adicionarItem(id, request);
    }

}