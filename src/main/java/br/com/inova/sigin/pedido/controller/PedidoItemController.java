package br.com.inova.sigin.pedido.controller;

import br.com.inova.sigin.pedido.dto.PedidoItemRequest;
import br.com.inova.sigin.pedido.dto.PedidoItemResponse;
import br.com.inova.sigin.pedido.service.PedidoItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @PutMapping("/{itemId}/quantidade")
    public ResponseEntity<PedidoItemResponse> alterarQuantidade(
            @PathVariable Long pedidoId,
            @PathVariable Long itemId,
            @RequestParam @Positive BigDecimal quantidade) {

        return ResponseEntity.ok(
                service.alterarQuantidade(
                        pedidoId,
                        itemId,
                        quantidade
                )
        );
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> remover(
            @PathVariable Long pedidoId,
            @PathVariable Long itemId) {

        service.remover(pedidoId, itemId);

        return ResponseEntity.noContent().build();
    }
}