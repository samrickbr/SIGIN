package br.com.inova.sigin.api.delivery.cliente.controller;

import br.com.inova.sigin.api.delivery.cliente.dto.ClienteRequest;
import br.com.inova.sigin.api.delivery.cliente.dto.ClienteResponse;
import br.com.inova.sigin.api.delivery.cliente.service.ClienteDeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteDeliveryService service;
    @PostMapping
    public ClienteResponse criar(
            @RequestBody @Valid ClienteRequest request
    ) {
        return service.criar(request);
    }
    @GetMapping("/telefone/{telefone}")
    public ClienteResponse buscar(
            @PathVariable String telefone
    ) {
        return service.buscarPorTelefone(telefone);
    }
    @GetMapping("/documento/{documento}")
    public ClienteResponse buscarPorDocumento(
            @PathVariable String documento
    ) {
        return service.buscarPorDocumento(documento);
    }
}