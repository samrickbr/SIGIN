package br.com.inova.sigin.dev.controller;

import br.com.inova.sigin.dev.dto.FluxoPedidoResponse;
import br.com.inova.sigin.dev.service.DevService;
import br.com.inova.sigin.fluxo.service.FluxoPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/dev")
@RequiredArgsConstructor
public class DevController {

    private final DevService service;
    private final FluxoPedidoService fluxoPedidoService;

    @PostMapping("/popular")
    public ResponseEntity<List<String>> popular() {
        return ResponseEntity.ok(service.popular());
    }

    @PostMapping("/fluxo/pedido")
    public ResponseEntity<FluxoPedidoResponse> fluxoPedido() {
        return ResponseEntity.ok(
                fluxoPedidoService.executar()
        );
    }
}