package br.com.inova.sigin.pedido.controller;

import br.com.inova.sigin.pedido.dto.PedidoItemRequest;
import br.com.inova.sigin.pedido.dto.PedidoItemResponse;
import br.com.inova.sigin.pedido.service.PedidoItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos/{pedidoId}/itens")
@RequiredArgsConstructor
public class PedidoItemController {

    private final PedidoItemService service;


    @PostMapping
    public ResponseEntity<PedidoItemResponse> adicionar(
            @PathVariable Long pedidoId,
            @Valid @RequestBody PedidoItemRequest request) {

        return ResponseEntity.ok(
                service.adicionar(pedidoId, request)
        );
    }


    @GetMapping
    public ResponseEntity<List<PedidoItemResponse>> listar(
            @PathVariable Long pedidoId) {

        return ResponseEntity.ok(
                service.listar(pedidoId)
        );
    }
}